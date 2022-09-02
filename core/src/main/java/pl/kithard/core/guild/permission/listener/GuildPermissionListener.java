package pl.kithard.core.guild.permission.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.guild.permission.GuildPermission;

public class GuildPermissionListener implements Listener {

    private final CorePlugin plugin;
    private final GuildCache guildCache;

    public GuildPermissionListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.guildCache = plugin.getGuildCache();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        Location location = event.getBlock().getLocation();
        if (material == Material.BEACON && this.guildCache.isNotAllowed(player, location, GuildPermission.BEACON)) {
            event.setCancelled(true);
        }
        else if (material == Material.OBSIDIAN && this.guildCache.isNotAllowed(player, location, GuildPermission.OBSIDIAN_BREAK)) {
            event.setCancelled(true);
        }
        else if (material == Material.CHEST && this.guildCache.isNotAllowed(player, location, GuildPermission.CHEST_ACCESS)) {
            event.setCancelled(true);
        }
        else if (material == Material.HOPPER && this.guildCache.isNotAllowed(player, location, GuildPermission.HOPPER_ACCESS)) {
            event.setCancelled(true);
        }
        else if (this.guildCache.isNotAllowed(player, location, GuildPermission.BLOCK_BREAK)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Material material = event.getBlock().getType();
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        if (material == Material.OBSIDIAN && this.guildCache.isNotAllowed(player, location, GuildPermission.OBSIDIAN_PLACE)) {
            event.setCancelled(true);
        }
        else if (material == Material.TNT && this.guildCache.isNotAllowed(player, location, GuildPermission.TNT_PLACEMENT)) {
            event.setCancelled(true);
        }
        else if (material == Material.ANVIL && this.guildCache.isNotAllowed(player, location, GuildPermission.ANVIL_PLACEMENT)) {
            event.setCancelled(true);
        }
        else if (material == Material.REDSTONE || material == Material.REDSTONE_BLOCK) {
            if (this.guildCache.isNotAllowed(player, location, GuildPermission.RED_STONE_PLACEMENT)) {
                event.setCancelled(true);
            }
        }
        else if (material == Material.PISTON_BASE || material == Material.PISTON_STICKY_BASE) {
            if (this.guildCache.isNotAllowed(player, location, GuildPermission.PISTON_PLACEMENT)) {
                event.setCancelled(true);
            }
        }
        else if (material == Material.SAND && this.guildCache.isNotAllowed(player, location, GuildPermission.SAND_PLACEMENT)) {
            event.setCancelled(true);
        }
        else if (material == Material.GRAVEL && this.guildCache.isNotAllowed(player, location, GuildPermission.GRAVEL_PLACEMENT)) {
            event.setCancelled(true);
        }
        else if (this.guildCache.isNotAllowed(player, location, GuildPermission.BLOCK_PLACE)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (block.getType() == Material.BEACON && this.guildCache.isNotAllowed(player, block.getLocation(), GuildPermission.BEACON_USE)) {
            event.setCancelled(true);
        }
        else if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
            if (this.guildCache.isNotAllowed(player, block.getLocation(), GuildPermission.CHEST_ACCESS)) {
                event.setCancelled(true);
            }
        }
        else if (block.getType() == Material.HOPPER && this.guildCache.isNotAllowed(player, block.getLocation(), GuildPermission.HOPPER_ACCESS)) {
            event.setCancelled(true);
        }
        else if (player.getItemInHand().getType() == Material.FLINT_AND_STEEL && this.guildCache.isNotAllowed(player, block.getLocation(), GuildPermission.FLINT_USE)) {
            event.setCancelled(true);
        }

    }
    
    @EventHandler(ignoreCancelled = true)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        if (event.getBucket() == Material.WATER_BUCKET && this.guildCache.isNotAllowed(player, event.getBlockClicked().getLocation(), GuildPermission.WATER_USE)) {
            event.setCancelled(true);
        }
        else if (event.getBucket() == Material.LAVA_BUCKET && this.guildCache.isNotAllowed(player, event.getBlockClicked().getLocation(), GuildPermission.LAVA_USE)) {
            event.setCancelled(true);
        }
    }


}
