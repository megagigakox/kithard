package pl.kithard.core.player.backup.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.backup.PlayerBackupType;

public class PlayerBackupTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public PlayerBackupTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(this.plugin, 20 * 2700, 20 * 2700);
    }

    @Override
    public void run() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.plugin.getPlayerBackupFactory().create(player, PlayerBackupType.AUTO, "unknown killer", 0);
        }
    }
}
