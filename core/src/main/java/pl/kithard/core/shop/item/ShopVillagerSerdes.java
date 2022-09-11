package pl.kithard.core.shop.item;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShopVillagerSerdes implements ObjectSerializer<ShopVillager> {

    @Override
    public boolean supports(@NotNull Class<? super ShopVillager> type) {
        return ShopVillager.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ShopVillager object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("icon", object.getIcon());
        data.addCollection("items", object.getItems(), ShopVillagerItem.class);
    }

    @Override
    public ShopVillager deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new ShopVillager(
                data.get("name", String.class),
                data.get("icon", ItemStack.class),
                data.getAsList("items", ShopVillagerItem.class)
        );
    }
}
