package pl.kithard.core.antimacro;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;

public class AntiMacroTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public AntiMacroTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20L);
    }

    @Override
    public void run() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.plugin.getAntiMacroCache().getUuidClicksPerSecondMap().put(player.getUniqueId(), 0);
        }
    }
}
