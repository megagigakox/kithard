package pl.kithard.core.shop.item;

import org.bukkit.inventory.ItemStack;

public class ShopVillagerItem {

    private final String name;
    private final double price;
    private final ItemStack item;

    public ShopVillagerItem(String name, double price, ItemStack item) {
        this.name = name;
        this.price = price;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ItemStack getItem() {
        return item;
    }
}
