package pl.kithard.core.shop.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopBuyItem {

    private final String name;
    private final double price;
    private final List<String> commands;
    private final ItemStack inGui;
    private final ItemStack afterBuy;

    public ShopBuyItem(String name, double price, List<String> commands, ItemStack inGui, ItemStack afterBuy) {
        this.name = name;
        this.price = price;
        this.commands = commands;
        this.inGui = inGui;
        this.afterBuy = afterBuy;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getCommands() {
        return commands;
    }

    public ItemStack getInGui() {
        return inGui;
    }

    public ItemStack getAfterBuy() {
        return afterBuy;
    }
}
