package pl.kithard.proxy.mojang;

import com.google.gson.*;
import pl.kithard.proxy.ProxyPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MojangRequestTask implements Runnable{
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public MojangRequestTask(ProxyPlugin plugin) {
        plugin.getProxy().getScheduler().schedule(plugin, this, 2L, 2L, TimeUnit.SECONDS);
    }

    private static void requestNormal(
            Set<Entry<String, CompletableFuture<Entry<String, Boolean>>>> requests)
            throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(
                "https://api.mojang.com/profiles/minecraft").openConnection();
        try {
            Map<String, Boolean> statusMap = new HashMap<>();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                JsonArray array = new JsonArray();
                requests.forEach(entry -> array.add(new JsonPrimitive(entry.getKey())));
                writer.write(array.toString());
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                JsonArray array = GSON.fromJson(reader.lines().collect(Collectors.joining()),
                        JsonArray.class);
                for (JsonElement element : array) {
                    if (!element.isJsonObject()) {
                        continue;
                    }

                    JsonObject object = element.getAsJsonObject();
                    if (!object.has("name")) {
                        throw new IOException("Cannot verify the requests. (NO NICKNAME)");
                    }

                    statusMap.put(object.get("name").getAsString(), true);
                }
            }

            for (Entry<String, CompletableFuture<Entry<String, Boolean>>> request : requests) {
                if (!statusMap.containsKey(request.getKey())) {
                    statusMap.put(request.getKey(), false);
                }
            }

            Map<String, Entry<String, Boolean>> fixedStatusMap = statusMap.entrySet().stream().map(
                            entry -> new SimpleEntry<>(entry.getKey().toLowerCase(),
                                    new SimpleEntry<>(entry.getKey(), entry.getValue())))
                    .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
            MojangUtil.completeRequests(requests, fixedStatusMap);
            MojangUtil.cacheStatuses(fixedStatusMap);
        } finally {
            connection.disconnect();
        }
    }

    private static void requestFailover(Set<Entry<String, CompletableFuture<Entry<String, Boolean>>>> requests) throws IOException {
        Map<String, Boolean> statusMap = new HashMap<>();
        for (Entry<String, CompletableFuture<Entry<String, Boolean>>> entry : requests) {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    "https://api.minetools.eu/uuid/" + entry.getKey()).openConnection();
            try {
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoInput(true);
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    JsonObject object = GSON.fromJson(reader.lines().collect(Collectors.joining()),
                            JsonObject.class);
                    if (!object.has("status")) {
                        throw new IOException("Cannot verify the requests. (NO STATUS)");
                    }

                    String status = object.get("status").getAsString();
                    if (!status.equals("OK") && !status.equals("ERR")) {
                        throw new IOException("Cannot verify the requests. (NO OK/ERR STATUS)");
                    }

                    if (!object.has("name")) {
                        throw new IOException("Cannot verify the requests. (NO NICKNAME)");
                    }

                    statusMap.put(object.get("name").getAsString(), status.equals("OK"));
                }
            } finally {
                connection.disconnect();
            }
        }

        Map<String, Entry<String, Boolean>> fixedStatusMap = statusMap.entrySet().stream()
                .map(entry -> new SimpleEntry<>(entry.getKey().toLowerCase(),
                        new SimpleEntry<>(entry.getKey(), entry.getValue())))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
        MojangUtil.completeRequests(requests, fixedStatusMap);
        MojangUtil.cacheStatuses(fixedStatusMap);
    }

    @Override
    public void run() {
         Set<Entry<String, CompletableFuture<Entry<String, Boolean>>>> requests = MojangUtil.takeTenRequests();
        if (!requests.isEmpty()) {
            try {
                requestNormal(requests);
            } catch ( IOException e) {
                try {
                    requestFailover(requests);
                } catch (IOException ex) {
                    MojangUtil.errorRequests();
                    System.out.println("blad z weryfikacja kont");
                }
            }
        }
    }
}