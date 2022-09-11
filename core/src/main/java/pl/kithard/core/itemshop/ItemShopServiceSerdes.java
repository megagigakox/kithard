package pl.kithard.core.itemshop;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemShopServiceSerdes implements ObjectSerializer<ItemShopService> {
    @Override
    public boolean supports(@NotNull Class<? super ItemShopService> type) {
        return ItemShopService.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull ItemShopService object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.addCollection("messages", object.getMessages(), String.class);
        data.addCollection("commands", object.getCommands(), String.class);
        data.addCollection("items", object.getItems(), ItemStack.class);
    }

    @Override
    public ItemShopService deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new ItemShopService(
                data.get("name", String.class),
                data.getAsList("commands", String.class),
                data.getAsList("messages", String.class),
                data.getAsList("items", ItemStack.class));
    }
}
