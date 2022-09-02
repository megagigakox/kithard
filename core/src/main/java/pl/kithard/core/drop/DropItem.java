package pl.kithard.core.drop;

import org.bukkit.inventory.ItemStack;

public class DropItem {

    private final String name;

    private final ItemStack item;
    private final double chance;
    private final int exp;
    private final boolean fortune;
    private final boolean guildItem;

    public DropItem(String name, ItemStack item, double chance, int exp, boolean fortune, boolean guildItem) {
        this.name = name;
        this.item = item;
        this.chance = chance;
        this.exp = exp;
        this.fortune = fortune;
        this.guildItem = guildItem;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isFortune() {
        return fortune;
    }

    public double getChance() {
        return chance;
    }

    public int getExp() {
        return exp;
    }

    public boolean isGuildItem() {
        return guildItem;
    }
}
