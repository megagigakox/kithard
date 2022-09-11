package pl.kithard.core.shop.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopVillager {

    private final String name;
    private final ItemStack icon;
    private final List<ShopVillagerItem> items;

    public ShopVillager(String name, ItemStack icon, List<ShopVillagerItem> items) {
        this.name = name;
        this.icon = icon;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public List<ShopVillagerItem> getItems() {
        return items;
    }
}
