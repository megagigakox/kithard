package pl.kithard.core.player.enderchest.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.util.TextUtil;

public class EnderChestListener implements Listener {

    private final CorePlugin plugin;

    public EnderChestListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getName().equalsIgnoreCase("Ender Chest")) {
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName() == null) {
                return;
            }

            if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(TextUtil.color("&a&lAby odblokowac, zakup range premium!"))) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getName().contains("EnderChest")) {
            String[] split = ChatColor.stripColor(inventory.getName()).split(":\\s*");
            String name = split[1];
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(name);
            if (inventory.getContents() != null) {
                corePlayer.getEnderChest().setContents(inventory.getContents());
            }
            corePlayer.setNeedSave(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.ENDER_CHEST) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(event.getPlayer());
        if (corePlayer.getCombat().hasFight()) {
            TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cTa czynnosc jest zablokowana podczas walki");
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        corePlayer.getEnderChest().openInventory(corePlayer.source(), corePlayer.source());
    }
}
