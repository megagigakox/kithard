package pl.kithard.core.util.adapters;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.ItemStackSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class InventoryAdapter implements JsonDeserializer<Inventory>, JsonSerializer<Inventory> {

    @Override
    public Inventory deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return fromJson(jsonElement);
    }

    @Override
    public JsonElement serialize(Inventory itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {
        return toJson(itemStacks);
    }

    public static JsonObject toJson(Inventory inventory) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("title", inventory.getTitle());
        jsonObject.addProperty("size", inventory.getSize());
        jsonObject.addProperty("contents", ItemStackSerializer.itemStackArrayToBase64(inventory.getContents()));

        return jsonObject;
    }

    public static Inventory fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String contents = jsonObject.get("contents").getAsString();
        ItemStack[] itemStacks = null;
        try {
            itemStacks = ItemStackSerializer.itemStackArrayFromBase64(contents);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String title = jsonObject.get("title").getAsString();
        int size = jsonObject.get("size").getAsInt();

        Inventory inventory = Bukkit.createInventory(null, size, title);
        inventory.setContents(itemStacks);

        return inventory;
    }

}
