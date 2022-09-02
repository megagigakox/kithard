package pl.kithard.core.util.adapters;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigurationSerializableAdapter
        implements JsonSerializer<ConfigurationSerializable>, JsonDeserializer<ConfigurationSerializable> {

    private final Type objectStringMapType = new TypeToken<Map<String, Object>>() {}.getType();

    @Override
    public ConfigurationSerializable deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
            JsonElement value = entry.getValue();
            String name = entry.getKey();

            if (value.isJsonObject() && value.getAsJsonObject().has(ConfigurationSerialization.SERIALIZED_TYPE_KEY)) {
                map.put(name, this.deserialize(value, value.getClass(), context));
            } else {
                map.put(name, context.deserialize(value, Object.class));
            }
        }

        return ConfigurationSerialization.deserializeObject(map);
    }

    @Override
    public JsonElement serialize(
            ConfigurationSerializable src,
            Type typeOfSrc,
            JsonSerializationContext context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(src.getClass()));
        map.putAll(src.serialize());
        return context.serialize(map, objectStringMapType);
    }
}