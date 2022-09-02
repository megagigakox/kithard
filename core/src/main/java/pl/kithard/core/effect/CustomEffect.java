package pl.kithard.core.effect;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class CustomEffect {

    private final String name;
    private final ItemStack icon;
    private final int cost;
    private final int time;
    private final int slot;
    private final List<PotionEffect> potionEffects;

    public CustomEffect(String name, ItemStack icon, int cost, int time, int slot) {
        this.name = name;
        this.icon = icon;
        this.cost = cost;
        this.time = time;
        this.slot = slot;
        this.potionEffects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public int getSlot() {
        return slot;
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }
}
