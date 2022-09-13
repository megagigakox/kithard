package pl.kithard.core.database.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;

public class DataSaveTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public DataSaveTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 20 * 20, 20 * 20);
    }

    @Override
    public void run() {
        this.plugin.getCorePlayerFactory().saveAll(true);
        this.plugin.getGuildFactory().saveAll(true);
    }
}
