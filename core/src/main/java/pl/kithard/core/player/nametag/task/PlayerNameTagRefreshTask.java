package pl.kithard.core.player.nametag.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;

public class PlayerNameTagRefreshTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public PlayerNameTagRefreshTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 100L,100L);
    }

    @Override
    public void run() {
        for (Player it : Bukkit.getOnlinePlayers()) {
            this.plugin.getPlayerNameTagService().send(it);
        }
    }
}
