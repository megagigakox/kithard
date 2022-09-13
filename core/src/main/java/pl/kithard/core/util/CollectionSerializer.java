package pl.kithard.core.util;

import java.util.*;

public final class CollectionSerializer {

    private CollectionSerializer() {}

    public static String serializeCollection(Collection<String> collection) {
        if (collection.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : collection) {
            stringBuilder.append(string).append("@");
        }
        return stringBuilder.toString();
    }

    public static Collection<String> deserializeCollection(String serializedData) {
        Set<String> set = new HashSet<>();
        String[] split = serializedData.split("@");
        Collections.addAll(set, split);
        return set;
    }

    public static String serializeMap(Map<String, Integer> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }

        return stringBuilder.toString();
    }

    public static Map<String, Integer> deserializeMap(String serializedData) {
        Map<String, Integer> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(mapSplit[0], Integer.parseInt(mapSplit[1]));
            }
        }

        return map;
    }

    public static String serializeMapInteger(Map<Integer, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }

        return stringBuilder.toString();
    }

    public static Map<Integer, String> deserializeMapInteger(String serializedData) {
        Map<Integer, String> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(Integer.valueOf(mapSplit[0]), mapSplit[1]);
            }
        }

        return map;
    }

    public static String serializeMapLong(Map<String, Long> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("@");
        }
        return stringBuilder.toString();
    }

    public static Map<String, Long> deserializeMapLong(String serializedData) {
        Map<String, Long> map = new HashMap<>();
        String[] split = serializedData.split("@");
        for (String string : split) {
            String[] mapSplit = string.split("=");
            if (mapSplit.length >= 1) {
                map.put(mapSplit[0], Long.parseLong(mapSplit[1]));
            }
        }
        return map;
    }
}
