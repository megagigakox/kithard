package pl.kithard.core.player.combat.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

public class BlockCombatPlaceListener implements Listener {

    private final CorePlugin plugin;

    public BlockCombatPlaceListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlaceAntilogout(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockPlaced().getLocation());
        Block block = event.getBlock();

        if (player.getWorld().getName().equals("gtp") && block.getY() > 6) {
            event.setCancelled(true);
            return;
        }

        if (guild != null && guild.isMember(player.getUniqueId()) && corePlayer.getCombat().hasFight()) {
            event.setCancelled(true);
            TextUtil.message(player, "&8(&4&l!&8) &cBudowanie podczas pvp na terenie swojej gildii jest zablokowane!");
            return;
        }

        if (block.getY() > 100 && corePlayer.getCombat().hasFight()) {
            event.setCancelled(true);
            TextUtil.message(player, "&8(&4&l!&8) &cBudowanie podczas pvp powyzej poziomu 100 jest zablokowane!");
        }

    }
}
