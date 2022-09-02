package pl.kithard.core.drop.special;

import org.bukkit.inventory.ItemStack;

public class SpecialDropItem {

    private final String name;

    private final ItemStack item;
    private final double chance;
    private final SpecialDropItemType type;

    public SpecialDropItem(String name, ItemStack item, double chance, SpecialDropItemType type) {
        this.name = name;
        this.item = item;
        this.chance = chance;
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

    public SpecialDropItemType getType() {
        return type;
    }
}
