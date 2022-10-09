package pl.kithard.core.guild.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.border.util.BorderUtil;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

public class GuildTerrainActionsListener implements Listener {

    private final CorePlugin plugin;

    public GuildTerrainActionsListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(cancelAction(event.getPlayer(), event.getBlock().getLocation()));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(cancelAction(event.getPlayer(), event.getBlock().getLocation()));
    }

    @EventHandler(ignoreCancelled = true)
    public void water2(PlayerBucketFillEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockClicked().getLocation());
        if (guild != null && !guild.isMember(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void water(PlayerBucketEmptyEvent event) {
        if (LocationUtil.isInSpawn(event.getBlockClicked().getLocation())) {
            event.setCancelled(true);
            return;
        }

        if (event.getBucket() != Material.WATER_BUCKET) {
            return;
        }

        Player player = event.getPlayer();
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockClicked().getLocation());
        Block clickedBlock = event.getBlockClicked().getRelative(event.getBlockFace());

        if (guild == null) {
            event.setCancelled(true);

            clickedBlock.setType(Material.STATIONARY_WATER);
            player.getItemInHand().setType(Material.BUCKET);
            player.updateInventory();
        }

        else if (!guild.isMember(player.getUniqueId())) {
            event.setCancelled(true);

            clickedBlock.setType(Material.STATIONARY_WATER);
            player.getItemInHand().setType(Material.BUCKET);
            player.updateInventory();

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                clickedBlock.setType(Material.AIR);
                player.getInventory().remove(Material.BUCKET);
                player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 1));
                player.updateInventory();
            }, 20 * 5);
        }

    }

    public boolean cancelAction(Player player, Location location) {
        if (player.hasPermission("kithard.guild.admnin")) {
            return false;
        }

        Guild guild = this.plugin.getGuildCache().findByLocation(location);
        if (guild == null) {
            return false;
        }

        if (guild.isMember(player.getUniqueId())) {
            return false;
        }

        TextUtil.message(player, "&8(&4&l!&8) &cNie możesz tego zrobic na terenie wrogiej gildii!");
        return true;

    }

}
