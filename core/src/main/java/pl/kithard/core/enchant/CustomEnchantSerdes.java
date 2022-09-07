package pl.kithard.core.enchant;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

public class CustomEnchantSerdes implements ObjectSerializer<CustomEnchant> {
    @Override
    public boolean supports(@NotNull Class<? super CustomEnchant> type) {
        return CustomEnchant.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull CustomEnchant object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("slot", object.getSlot());
        data.add("cost", object.getCost());
        data.add("level", object.getLevel());
        data.add("enchantment", object.getEnchantment().getName());
    }

    @Override
    public CustomEnchant deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new CustomEnchant(data.get("name", String.class), data.get("slot", Integer.class), data.get("cost", Integer.class), data.get("level", Integer.class), Enchantment.getByName(data.get("enchantment", String.class)));
    }
}
