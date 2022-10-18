package pl.kithard.core.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;

public class RankingsRefreshTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public RankingsRefreshTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 20 * 30L);
    }

    @Override
    public void run() {
        this.plugin.getPlayerRankingService().sort();
        this.plugin.getGuildRankingService().sort();
    }
}
