package pl.kithard.core.guild.listener;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
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
