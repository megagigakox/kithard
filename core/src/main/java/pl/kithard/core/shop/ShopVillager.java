package pl.kithard.core.shop;

import org.bukkit.inventory.ItemStack;
import pl.kithard.core.shop.item.ShopVillagerItem;

import java.util.ArrayList;
import java.util.List;

public class ShopVillager {

    private final String name;
    private final ItemStack item;
    private final List<ShopVillagerItem> items = new ArrayList<>();

    public ShopVillager(String name, ItemStack item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItem() {
        return item;
    }

    public List<ShopVillagerItem> getItems() {
        return items;
    }
}
