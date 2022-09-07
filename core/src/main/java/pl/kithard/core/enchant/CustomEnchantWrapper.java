package pl.kithard.core.enchant;

import java.util.Collection;

public class CustomEnchantWrapper {

    private final CustomEnchantType type;
    private final Collection<CustomEnchant> enchantments;

    public CustomEnchantWrapper(CustomEnchantType type, Collection<CustomEnchant> enchantments) {
        this.type = type;
        this.enchantments = enchantments;
    }

    public CustomEnchantType getType() {
        return type;
    }

    public Collection<CustomEnchant> getEnchantments() {
        return enchantments;
    }
}
