package pl.kithard.core.settings;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

public class ServerSettingsSerdes implements ObjectSerializer<ServerSettings> {

    @Override
    public boolean supports(@NotNull Class<? super ServerSettings> type) {
        return ServerSettings.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ServerSettings object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("turbo-drop", object.getTurboDrop());
        data.add("freeze", object.getFreeze());
        data.addCollection("enabled-settings", object.getEnabledSettings(), ServerSettingsType.class);
    }

    @Override
    public ServerSettings deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new ServerSettings(
                data.get("turbo-drop", Long.class),
                data.get("freeze", Long.class),
                data.getAsList("enabled-settings", ServerSettingsType.class)
        );
    }
}
