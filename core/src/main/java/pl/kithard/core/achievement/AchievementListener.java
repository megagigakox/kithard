package pl.kithard.core.achievement;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

public class AchievementListener implements Listener {

    private final CorePlugin plugin;

    public AchievementListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.STONE) {
            return;
        }

        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        corePlayer.addAchievementProgress(AchievementType.MINED_STONE, 1);
    }
}
