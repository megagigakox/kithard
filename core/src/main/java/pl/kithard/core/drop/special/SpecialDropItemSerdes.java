package pl.kithard.core.drop.special;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SpecialDropItemSerdes implements ObjectSerializer<SpecialDropItem> {
    @Override
    public boolean supports(@NotNull Class<? super SpecialDropItem> type) {
        return SpecialDropItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull SpecialDropItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("type", object.getType());
        data.add("name", object.getName());
        data.add("item", object.getItem());
        data.add("min", object.getMin());
        data.add("max", object.getMax());
        data.add("chance", object.getChance());
    }

    @Override
    public SpecialDropItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new SpecialDropItem(
                data.get("name", String.class),
                data.get("item", ItemStack.class),
                data.get("chance", Double.class),
                data.get("min", Integer.class),
                data.get("max", Integer.class),
                SpecialDropItemType.valueOf(data.get("type", String.class))
        );
    }
}
