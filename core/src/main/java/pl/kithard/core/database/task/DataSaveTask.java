package pl.kithard.core.database.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.safe.Safe;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DataSaveTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public DataSaveTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20 * 360L);
    }

    @Override
    public void run() {
        this.plugin.getCorePlayerRepository().updateAll(this.plugin.getCorePlayerCache().getValues()
                .stream()
                .filter(DatabaseEntry::isNeedSave)
                .collect(Collectors.toSet()));

        this.plugin.getGuildRepository().updateAll(this.plugin.getGuildCache().getValues()
                .stream()
                .filter(DatabaseEntry::isNeedSave)
                .collect(Collectors.toSet()));

        this.plugin.getSafeRepository().updateAll(this.plugin.getSafeCache().values()
                .stream()
                .filter(DatabaseEntry::isNeedSave)
                .collect(Collectors.toSet()));
    }
}
