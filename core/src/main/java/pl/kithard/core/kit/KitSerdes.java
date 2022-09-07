package pl.kithard.core.kit;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KitSerdes implements ObjectSerializer<Kit> {
    @Override
    public boolean supports(@NotNull Class<? super Kit> type) {
        return Kit.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Kit object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("permission", object.getPermission());
        data.add("guiSlot", object.getGuiSlot());
        data.add("cooldown", object.getCooldown());
        data.add("enable", object.isEnable());
        data.add("icon", object.getIcon());
        data.add("items", object.getItems());
    }

    @Override
    public Kit deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new Kit(
                data.get("name", String.class),
                data.get("guiSlot", Integer.class),
                data.get("permission", String.class),
                data.get("cooldown", Long.class),
                data.get("icon", ItemStack.class),
                data.getAsList("items", ItemStack.class));
    }
}
