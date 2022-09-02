package pl.kithard.core.util.adapters;

import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.ItemStackSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ItemStackArrayAdapter implements JsonDeserializer<ItemStack[]>, JsonSerializer<ItemStack[]> {

    @Override
    public ItemStack[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return fromJson(jsonElement);
    }

    @Override
    public JsonElement serialize(ItemStack[] itemStacks, Type type, JsonSerializationContext jsonSerializationContext) {
        return toJson(itemStacks);
    }

    public static JsonObject toJson(ItemStack[] itemStacks) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("contents", ItemStackSerializer.itemStackArrayToBase64(itemStacks));

        return jsonObject;
    }

    public static ItemStack[] fromJson(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String contents = jsonObject.get("contents").getAsString();
        ItemStack[] itemStacks = null;
        try {
            itemStacks = ItemStackSerializer.itemStackArrayFromBase64(contents);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return itemStacks;
    }

}
