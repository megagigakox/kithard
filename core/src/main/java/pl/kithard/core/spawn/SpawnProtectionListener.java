package pl.kithard.core.spawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

public class SpawnProtectionListener implements Listener {

    private final CorePlugin plugin;

    public SpawnProtectionListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (LocationUtil.isInProtectionArea(event.getBlock().getLocation()) && !player.hasPermission("kithard.spawn.build")) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tutaj tego zrobic!");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (LocationUtil.isInProtectionArea(event.getBlock().getLocation()) && !player.hasPermission("kithard.spawn.build")) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tutaj tego zrobic!");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPvp(EntityDamageEvent event) {
        Player player = (Player) event.getEntity();
        if (LocationUtil.isInSpawn(player.getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            if (LocationUtil.isInSpawn(event.getTo())) {
                event.setCancelled(true);
            }
        }
    }

}
