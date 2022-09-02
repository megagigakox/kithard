package pl.kithard.core.border.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.border.util.BorderUtil;

public class BorderListener implements Listener {

    public BorderListener(CorePlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (BorderUtil.isBorderNear(event.getBlock().getLocation(), 30)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty(BlockPlaceEvent event) {
        if (BorderUtil.isBorderNear(event.getBlock().getLocation(), 30)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (BorderUtil.isBorderNear(event.getBlockClicked().getLocation(), 30)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        if (BorderUtil.isBorderNear(event.getTo(), 5)) {
            event.setCancelled(true);
        }
    }

}
