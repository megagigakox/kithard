package pl.kithard.core.database.task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.safe.Safe;

import java.util.HashSet;
import java.util.Set;

public class DataSaveTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public DataSaveTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20 * 360L);
    }

    @Override
    public void run() {
        Set<CorePlayer> corePlayersNeedSave = new HashSet<>();
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (corePlayer.isNeedSave()) {
                corePlayersNeedSave.add(corePlayer);
            }
        }

        this.plugin.getCorePlayerRepository().updateAll(corePlayersNeedSave);

        Set<Guild> guildsMeedSave = new HashSet<>();
        for (Guild guild : this.plugin.getGuildCache().getValues()) {
            if (guild.isNeedSave()) {
                guildsMeedSave.add(guild);
            }
        }

        this.plugin.getGuildRepository().updateAll(guildsMeedSave);

        Set<Safe> safesNeddSave = new HashSet<>();
        for (Safe safe : this.plugin.getSafeCache().values()) {
            if (safe.isNeedSave()) {
                safesNeddSave.add(safe);
            }
        }

        this.plugin.getSafeRepository().updateAll(safesNeddSave);
    }
}
