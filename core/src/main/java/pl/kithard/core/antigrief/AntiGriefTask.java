package pl.kithard.core.antigrief;

import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;

public class AntiGriefTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public AntiGriefTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(this.plugin, 0L, 20 * 60L);
    }

    @Override
    public void run() {
        for (AntiGriefBlock block : this.plugin.getAntiGriefCache().getAntiGriefBlocks()) {
            boolean expired = block.hasExpired();
            if (expired) {
                block.clear();
            }
        }
    }
}
