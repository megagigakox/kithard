package pl.kithard.core.shop.item;

import org.bukkit.inventory.ItemStack;

public class ShopVillagerItem {

    private final String name;
    private final int price;
    private final ItemStack inGui;
    private final ItemStack afterBuy;

    public ShopVillagerItem(String name, int price, ItemStack inGui, ItemStack afterBuy) {
        this.name = name;
        this.price = price;
        this.inGui = inGui;
        this.afterBuy = afterBuy;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ItemStack getInGui() {
        return inGui;
    }

    public ItemStack getAfterBuy() {
        return afterBuy;
    }
}
