package pl.kithard.core.abyss;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AbbysCache {

    private final List<ItemStack> items = new ArrayList<>();
    private boolean enable;

    public AbbysCache(boolean enable) {
        this.enable = enable;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
