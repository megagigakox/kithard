package pl.kithard.core.player.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

public class BlockPlaceListener implements Listener {

    private final CorePlugin plugin;

    public BlockPlaceListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlaceAntilogout(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockPlaced().getLocation());
        if (guild != null && guild.isMember(player.getUniqueId()) && corePlayer.getCombat().hasFight()) {
            event.setCancelled(true);
            TextUtil.message(player, "&8[&4&l!&8] &cBudowanie podczas pvp na terenie swojej gildii jest zablokowane!");
            return;
        }

        if (player.getLocation().getY() > 100 && corePlayer.getCombat().hasFight()) {
            event.setCancelled(true);
            TextUtil.message(player, "&8[&4&l!&8] &cBudowanie podczas pvp powyzej poziomu 100 jest zablokowane!");
        }

    }
}
