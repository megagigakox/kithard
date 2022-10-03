package pl.kithard.core.player.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
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

    public void openInventory(Player source, Player viewer) {

        this.inventory = Bukkit.createInventory(null, 54, TextUtil.color("&7EnderChest&8: &3" + source.getName()));
        if (this.contents != null) {
            this.inventory.setContents(this.contents);
        }

        if (source.hasPermission("kithard.enderchests.6")) {
            for (int i = 27; i < 54; ++i) {
                ItemStack is = inventory.getItem(i);
                if (is == null) {
                    continue;
                }
                if ((is.getItemMeta() != null && is.getItemMeta().getDisplayName() != null) && is.getItemMeta().getDisplayName().equalsIgnoreCase(TextUtil.color("&a&lAby odblokowac, zakup range premium!"))) {
                    this.inventory.setItem(i, new ItemStack(Material.AIR));
                }
            }
        }
        else if (source.hasPermission("kithard.enderchests.5")) {
            for (int i = 27; i < 54; ++i) {
                ItemStack is = inventory.getItem(i);
                if (is == null) {
                    continue;
                }
                if ((is.getItemMeta() != null && is.getItemMeta().getDisplayName() != null) && is.getItemMeta().getDisplayName().equalsIgnoreCase(TextUtil.color("&a&lAby odblokowac, zakup range premium!"))) {
                    this.inventory.setItem(i, new ItemStack(Material.AIR));
                }
            }
            for (int i = 45; i < 54; ++i) {
                this.inventory.setItem(i, ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE)
                        .name("&a&lAby odblokowac, zakup range premium!")
                        .asItemStack());
            }
        }
        else if (source.hasPermission("kithard.enderchests.4")) {
            for (int i = 27; i < 54; ++i) {
                ItemStack is = inventory.getItem(i);
                if (is == null) {
                    continue;
                }
                if ((is.getItemMeta() != null && is.getItemMeta().getDisplayName() != null) && is.getItemMeta().getDisplayName().equalsIgnoreCase(TextUtil.color("&a&lAby odblokowac, zakup range premium!"))) {
                    this.inventory.setItem(i, new ItemStack(Material.AIR));
                }
            }
            for (int i = 36; i < 54; ++i) {
                this.inventory.setItem(i, ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE)
                        .name("&a&lAby odblokowac, zakup range premium!")
                        .asItemStack());
            }
        }
        else if (source.hasPermission("kithard.enderchests.3")) {
            for (int i = 27; i < 54; ++i) {
                this.inventory.setItem(i, new ItemStack(Material.AIR));
                this.inventory.setItem(i, ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE)
                        .name("&a&lAby odblokowac, zakup range premium!")
                        .asItemStack());
            }
        }



        viewer.openInventory(this.inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
