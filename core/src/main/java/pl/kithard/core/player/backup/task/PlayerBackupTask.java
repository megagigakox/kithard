package pl.kithard.core.player.backup.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.backup.PlayerBackupType;

public class PlayerBackupTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public PlayerBackupTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20 * 2700L);
    }

    @Override
    public void run() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.plugin.getPlayerBackupFactory().create(player, PlayerBackupType.AUTO, "unknown killer", 0);
        }
    }
}
