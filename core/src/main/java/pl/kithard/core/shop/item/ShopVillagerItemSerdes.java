package pl.kithard.core.shop.item;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShopVillagerItemSerdes implements ObjectSerializer<ShopVillagerItem> {

    @Override
    public boolean supports(@NotNull Class<? super ShopVillagerItem> type) {
        return ShopVillagerItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ShopVillagerItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("price", object.getPrice());
        data.add("item", object.getItem());
    }

    @Override
    public ShopVillagerItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new ShopVillagerItem(
                data.get("name", String.class),
                data.get("price", Double.class),
                data.get("item", ItemStack.class)
        );
    }
}
