package pl.kithard.core.shop.item;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShopItemSerdes implements ObjectSerializer<ShopItem> {

    @Override
    public boolean supports(@NotNull Class<? super ShopItem> type) {
        return ShopItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ShopItem object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("type", object.getType());
        data.add("price", object.getPrice());
        data.addCollection("commands", object.getCommands(), List.class);
        data.add("item", object.getItem());
    }

    @Override
    public ShopItem deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new ShopItem(
                ShopItemType.valueOf(data.get("type", String.class)),
                data.get("name", String.class),
                data.get("price", Double.class),
                data.getAsList("commands", String.class),
                data.get("item", ItemStack.class)
        );
    }
}
