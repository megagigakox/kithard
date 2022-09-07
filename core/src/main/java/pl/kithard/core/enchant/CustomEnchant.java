package pl.kithard.core.enchant;

import org.bukkit.enchantments.Enchantment;

public class CustomEnchant {

    private final String name;
    private final int slot;
    private final int cost;
    private final int level;
    private final Enchantment enchantment;

    public CustomEnchant(String name, int slot, int cost, int level, Enchantment enchantment) {
        this.name = name;
        this.slot = slot;
        this.cost = cost;
        this.level = level;
        this.enchantment = enchantment;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }
}
