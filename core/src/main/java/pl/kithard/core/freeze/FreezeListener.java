package pl.kithard.core.freeze;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.kithard.core.CorePlugin;

public class FreezeListener implements Listener {

    private final CorePlugin plugin;

    public FreezeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom(), to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis() && !event.getPlayer().hasPermission("kithard.freeze.bypass")) {
            event.setTo(event.getFrom());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis() && !event.getPlayer().hasPermission("kithard.freeze.bypass")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event) {
        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis() && !event.getPlayer().hasPermission("kithard.freeze.bypass")) {
            event.setCancelled(true);
        }
    }
}
