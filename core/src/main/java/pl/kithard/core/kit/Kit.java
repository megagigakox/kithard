package pl.kithard.core.kit;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    private final String name;
    private final String guiName;
    private final String permission;
    private final int guiSlot;
    private final String cooldown;
    private boolean status;
    private final ItemStack icon;
    private final List<String> lore = new ArrayList<>();
    private final List<ItemStack> items = new ArrayList<>();

    public Kit(String name, String guiName, int guiSlot, String permission, String cooldown, ItemStack icon) {
        this.name = name;
        this.guiName = guiName;
        this.guiSlot = guiSlot;
        this.permission = permission;
        this.cooldown = cooldown;
        this.icon = icon;
        this.status = true;
    }

    public String getName() {
        return name;
    }

    public String getGuiName() {
        return guiName;
    }

    public String getPermission() {
        return permission;
    }

    public int getGuiSlot() {
        return guiSlot;
    }

    public String getCooldown() {
        return cooldown;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
