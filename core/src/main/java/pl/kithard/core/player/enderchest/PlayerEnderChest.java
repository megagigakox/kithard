package pl.kithard.core.player.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

public class PlayerEnderChest {

    private Inventory inventory;
    private ItemStack[] contents;

    public PlayerEnderChest(ItemStack[] contents) {
        this.contents = contents;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    public void openInventory(Player player) {

        int rows = 3;
        if (player.hasPermission("kithard.enderchests.6")) {
            rows = 6;
        }
        else if (player.hasPermission("kithard.enderchests.5")) {
            rows = 5;
        }
        else if (player.hasPermission("kithard.enderchests.4")) {
            rows = 4;
        }

        this.inventory = Bukkit.createInventory(null, rows * 9, "Ender Chest");
        if (contents != null) {
            this.inventory.setContents(this.contents);
        }
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
