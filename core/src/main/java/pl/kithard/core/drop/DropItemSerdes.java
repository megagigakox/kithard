package pl.kithard.core.drop;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DropItemSerdes implements ObjectSerializer<DropItem> {
    @Override
    public boolean supports(@NotNull Class<? super DropItem> type) {
        return DropItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull DropItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("item", object.getItem());
        data.add("exp", object.getExp());
        data.add("chance", object.getChance());
        data.add("fortune", object.isFortune());
        data.add("guildItem", object.isGuildItem());
    }

    @Override
    public DropItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new DropItem(
                data.get("name", String.class),
                data.get("item", ItemStack.class),
                data.get("chance", Double.class),
                data.get("exp", Integer.class),
                data.get("fortune", Boolean.class),
                data.get("guildItem", Boolean.class)
        );
    }
}
