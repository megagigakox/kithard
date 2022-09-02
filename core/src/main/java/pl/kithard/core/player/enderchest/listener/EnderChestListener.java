package pl.kithard.core.player.enderchest.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.player.enderchest.gui.EnderChestGui;

public class EnderChestListener implements Listener {

    private final CorePlugin plugin;

    public EnderChestListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        for (PlayerEnderChest enderChest : corePlayer.getEnderChests()) {
            if (inventory.getName().equalsIgnoreCase(TextUtil.color(enderChest.getInventory().getName()))) {
                corePlayer.setNeedSave(true);
            }
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
            TextUtil.message(event.getPlayer(), "&8[&4&l!&8] &cTa czynnosc jest zablokowana podczas walki");
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        new EnderChestGui(plugin).open(event.getPlayer());
    }
}
