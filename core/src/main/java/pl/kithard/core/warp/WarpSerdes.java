package pl.kithard.core.warp;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class WarpSerdes implements ObjectSerializer<Warp> {
    @Override
    public boolean supports(@NotNull Class<? super Warp> type) {
        return Warp.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Warp object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("location", object.getLocation());
        data.add("icon", object.getIcon());
    }

    @Override
    public Warp deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new Warp(
                data.get("name", String.class),
                data.get("location", Location.class),
                data.get("icon", Material.class)
        );
    }
}
