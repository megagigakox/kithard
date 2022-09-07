package pl.kithard.core.enchant;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class CustomEnchantWrapperSerdes implements ObjectSerializer<CustomEnchantWrapper> {
    @Override
    public boolean supports(@NotNull Class<? super CustomEnchantWrapper> type) {
        return CustomEnchantWrapper.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull CustomEnchantWrapper object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("type", object.getType());
        data.addCollection("enchantments", object.getEnchantments(), CustomEnchant.class);
    }

    @Override
    public CustomEnchantWrapper deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        CustomEnchantType type = CustomEnchantType.valueOf(data.get("type", String.class));
        Collection<CustomEnchant> customEnchants =  data.getAsList("enchantments", CustomEnchant.class);
        return new CustomEnchantWrapper(type, customEnchants);
    }
}
