package pl.kithard.core.kit;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit {

    private final String name;

    private final String permission;
    private final int guiSlot;
    private long cooldown;
    private boolean enable;
    private final ItemStack icon;
    private final List<ItemStack> items;

    public Kit(String name, int guiSlot, String permission, long cooldown, boolean enable, ItemStack icon, List<ItemStack> items) {
        this.name = name;
        this.guiSlot = guiSlot;
        this.permission = permission;
        this.cooldown = cooldown;
        this.icon = icon;
        this.items = items;
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public long getCooldown() {
        return cooldown;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
