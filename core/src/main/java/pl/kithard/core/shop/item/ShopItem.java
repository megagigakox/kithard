package pl.kithard.core.shop.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItem {

    private final ShopItemType type;
    private final String name;
    private final double price;
    private final List<String> commands;
    private final ItemStack item;

    public ShopItem(ShopItemType type, String name, double price, List<String> commands, ItemStack item) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.commands = commands;
        this.item = item;
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

    public ItemStack getItem() {
        return item;
    }

    public ShopItemType getType() {
        return type;
    }
}
