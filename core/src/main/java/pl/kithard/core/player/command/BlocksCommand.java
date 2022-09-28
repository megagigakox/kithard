package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class BlocksCommand {

    @FunnyCommand(
            name = "bloki",
            acceptsExceeded = true
    )
    public void handle(Player player) {
        while (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.DIAMOND), 9)) {
                player.getInventory().removeItem( new ItemStack(Material.DIAMOND, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.DIAMOND_BLOCK, 1));
            }
        }

        while (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD), 9)) {
                player.getInventory().removeItem(new ItemStack(Material.EMERALD, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.EMERALD_BLOCK, 1));
            }
        }

        while (player.getInventory().containsAtLeast(new ItemStack(Material.COAL), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.COAL), 9)) {
                player.getInventory().removeItem(new ItemStack(Material.COAL, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.COAL_BLOCK, 1));
            }
        }

        while (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_INGOT), 9)) {
                player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.GOLD_BLOCK, 1));
            }
        }

        while (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.IRON_INGOT), 9)) {
                player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.IRON_BLOCK, 1));
            }
        }

        while (player.getInventory().containsAtLeast(new ItemStack(Material.REDSTONE), 9)) {
            if (player.getInventory().containsAtLeast(new ItemStack(Material.REDSTONE), 9)) {
                player.getInventory().removeItem(new ItemStack(Material.REDSTONE, 9));
                InventoryUtil.addItem(player, new ItemStack(Material.REDSTONE_BLOCK, 1));
            }
        }

        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie wymieniono wszystkie sztabki na bloki");

    }

}
