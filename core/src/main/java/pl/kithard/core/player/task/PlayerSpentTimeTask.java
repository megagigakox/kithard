package pl.kithard.core.player.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.player.CorePlayer;

public class PlayerSpentTimeTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public PlayerSpentTimeTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 20 * 30L);
    }

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (corePlayer == null) {
                continue;
            }
            long time = corePlayer.getSpendTime() + (currentTimeMillis - corePlayer.getLastTimeMeasurement());
            corePlayer.setSpendTime(time);
            corePlayer.setAchievementProgress(AchievementType.SPEND_TIME, time);
            corePlayer.setLastTimeMeasurement(System.currentTimeMillis());

        }
    }
}
