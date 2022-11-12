package pl.kithard.core.spawn;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.generator.Generator;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

public class SpawnProtectionListener implements Listener {

    private final CorePlugin plugin;

    public SpawnProtectionListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onFill(BlockBurnEvent event) {
        if (LocationUtil.isInProtectionArea(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (LocationUtil.isInProtectionArea(event.getBlock().getLocation()) && !player.hasPermission("kithard.spawn.build")) {

            Generator generator = this.plugin.getGeneratorCache().findByLocation(event.getBlock().getLocation());
            if (generator != null) {
                return;
            }

            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tutaj tego zrobic!");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (LocationUtil.isInProtectionArea(event.getBlock().getLocation()) && !player.hasPermission("kithard.spawn.build")) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tutaj tego zrobic!");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPvp(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (LocationUtil.isInSpawn(player.getLocation())) {
                event.setCancelled(true);
                return;
            }

            if (corePlayer.getTeleport() != null) {
                corePlayer.setTeleport(null);
            }
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
