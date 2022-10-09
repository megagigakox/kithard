package pl.kithard.core.freeze;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TitleUtil;

public class FreezeTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public FreezeTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis()) {
            long currentTimeMilis = System.currentTimeMillis();
            for (Player player : this.plugin.getServer().getOnlinePlayers()) {
                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
                corePlayer.setProtection(TimeUtil.timeFromString("5m") + currentTimeMilis);
                TitleUtil.title(player, "&b&lZAMROZENIE", "&7Start edycji zacznie sie za&8: &f" + TimeUtil.formatTimeMillis(this.plugin.getServerSettings().getFreeze() - System.currentTimeMillis()), 0, 60, 0);
            }
        }
    }
}
