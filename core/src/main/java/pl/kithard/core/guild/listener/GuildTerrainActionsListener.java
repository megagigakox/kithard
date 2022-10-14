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
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
        Player player = event.getPlayer();
        Block block = event.getBlock();
        boolean cancel = cancelAction(event.getPlayer(), block.getLocation());
        event.setCancelled(cancel);
        if (cancel && block.getType() != Material.SPONGE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 2));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLavaEmpty(PlayerBucketEmptyEvent event) {
        if (event.getBucket() == Material.LAVA_BUCKET) {
            event.setCancelled(cancelAction(event.getPlayer(), event.getBlockClicked().getLocation()));
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(cancelAction(event.getPlayer(), event.getBlock().getLocation()));
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
            if (player.getItemInHand().getType() != Material.BUCKET) {
                event.setCancelled(true);
            }

            clickedBlock.setType(Material.STATIONARY_WATER);
            player.getItemInHand().setType(Material.BUCKET);
            player.updateInventory();
            TextUtil.message(player, "&8(&3&l!&8) &7Postawiles wode na terenie &3wrogiej gildi&7, jezeli jej nie zabierzesz w ciagu &f10 sekund - zniknie!");

            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                clickedBlock.setType(Material.AIR);
            }, 20 * 10);
        }

        else if (guild.isMember(player.getUniqueId()) && clickedBlock.getLocation().getBlockY() >= 70) {
            event.setCancelled(true);
            clickedBlock.setType(Material.STATIONARY_WATER);
            player.getItemInHand().setType(Material.BUCKET);
            player.updateInventory();

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
