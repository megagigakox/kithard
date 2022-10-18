package pl.kithard.core.enchant;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public class CustomEnchantConfiguration extends OkaeriConfig {

    private Set<CustomEnchantWrapper> customEnchants = new HashSet<>();

    public CustomEnchantConfiguration() {
        this.customEnchants.add(new CustomEnchantWrapper(CustomEnchantType.BOOTS, Arrays.asList(
                new CustomEnchant("OCHRONA 4", 10, 30, 4, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 3", 19, 25, 3, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 2", 28, 20, 2, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 1", 37, 15, 1, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("NIEZNISZCZALNOSC 3", 11, 30, 3, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 2", 20, 20, 2, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 1", 29, 15, 1, Enchantment.DURABILITY),
                new CustomEnchant("FEATHER FALLING 2", 12, 40, 2, Enchantment.PROTECTION_FALL),
                new CustomEnchant("FEATHER FALLING 1", 21, 30, 1, Enchantment.PROTECTION_FALL)))

        );
        this.customEnchants.add(new CustomEnchantWrapper(CustomEnchantType.ARMOR, Arrays.asList(
                new CustomEnchant("OCHRONA 4", 10, 30, 4, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 3", 19, 25, 3, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 2", 28, 20, 2, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("OCHRONA 1", 37, 15, 1, Enchantment.PROTECTION_ENVIRONMENTAL),
                new CustomEnchant("NIEZNISZCZALNOSC 3", 11, 30, 3, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 2", 20, 20, 2, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 1", 29, 15, 1, Enchantment.DURABILITY)))
        );

        this.customEnchants.add(new CustomEnchantWrapper(CustomEnchantType.SWORD, Arrays.asList(
                new CustomEnchant("SHARPNESS 4", 10, 30, 4, Enchantment.DAMAGE_ALL),
                new CustomEnchant("SHARPNESS 3", 19, 20, 3, Enchantment.DAMAGE_ALL),
                new CustomEnchant("SHARPNESS 2", 28, 15, 2, Enchantment.DAMAGE_ALL),
                new CustomEnchant("SHARPNESS 1", 37, 10, 1, Enchantment.DAMAGE_ALL),
                new CustomEnchant("ZAKLETY OGIEN 1", 11, 25, 1, Enchantment.FIRE_ASPECT),
                new CustomEnchant("NIEZNISZCZALNOSC 3", 12, 30, 3, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 2", 21, 20, 2, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 1", 30, 15, 1, Enchantment.DURABILITY),
                new CustomEnchant("KNOCKBACK 2", 13, 40, 2, Enchantment.KNOCKBACK),
                new CustomEnchant("KNOCKBACK 1", 22, 25, 1, Enchantment.KNOCKBACK))));

        this.customEnchants.add(new CustomEnchantWrapper(CustomEnchantType.TOOLS, Arrays.asList(
                new CustomEnchant("WYDAJNOSC 5", 10, 40, 5, Enchantment.DIG_SPEED),
                new CustomEnchant("WYDAJNOSC 4", 19, 30, 4, Enchantment.DIG_SPEED),
                new CustomEnchant("WYDAJNOSC 3", 28, 25, 3, Enchantment.DIG_SPEED),
                new CustomEnchant("WYDAJNOSC 2", 37, 20, 2, Enchantment.DIG_SPEED),
                new CustomEnchant("WYDAJNOSC 1", 46, 15, 1, Enchantment.DIG_SPEED),
                new CustomEnchant("NIEZNISZCZALNOSC 3", 11, 30, 3, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 2", 20, 20, 2, Enchantment.DURABILITY),
                new CustomEnchant("NIEZNISZCZALNOSC 1", 29, 15, 1, Enchantment.DURABILITY),
                new CustomEnchant("FORTUNA 3", 12, 50, 3, Enchantment.LOOT_BONUS_BLOCKS),
                new CustomEnchant("FORTUNA 2", 21, 40, 2, Enchantment.LOOT_BONUS_BLOCKS),
                new CustomEnchant("FORTUNA 1", 30, 30, 1, Enchantment.LOOT_BONUS_BLOCKS)))
        );

        this.customEnchants.add(new CustomEnchantWrapper(CustomEnchantType.BOW, Arrays.asList(
                new CustomEnchant("POWER 3", 10, 30, 3, Enchantment.ARROW_DAMAGE),
                new CustomEnchant("POWER 2", 19, 20, 2, Enchantment.ARROW_DAMAGE),
                new CustomEnchant("POWER 1", 28, 15, 1, Enchantment.ARROW_DAMAGE),
                new CustomEnchant("FLAME 2", 11, 30, 2, Enchantment.ARROW_FIRE),
                new CustomEnchant("FLAME 1", 20, 20, 1, Enchantment.ARROW_FIRE))));
    }

    public CustomEnchantWrapper findByType(CustomEnchantType type) {
        for (CustomEnchantWrapper wrapper : customEnchants) {
            if (wrapper.getType() == type) {
                return wrapper;
            }
        }
        return null;
    }

    public CustomEnchantType findType(String name) {
        for (CustomEnchantType customEnchantType : CustomEnchantType.values()) {
            for (String s : customEnchantType.getMustContain()) {
                if (name.contains(s)) {
                    return customEnchantType;
                }
            }
        }
        return null;
    }

}
