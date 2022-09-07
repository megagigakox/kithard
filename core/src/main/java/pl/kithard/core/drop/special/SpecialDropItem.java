package pl.kithard.core.drop.special;

import org.bukkit.inventory.ItemStack;

public class SpecialDropItem {

    private final String name;

    private final ItemStack item;
    private final double chance;
    private final int min, max;
    private final SpecialDropItemType type;

    public SpecialDropItem(String name, ItemStack item, double chance, int min, int max, SpecialDropItemType type) {
        this.name = name;
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getChance() {
        return chance;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public SpecialDropItemType getType() {
        return type;
    }
}
