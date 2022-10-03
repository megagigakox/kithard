package pl.kithard.core.automessage.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.TextUtil;

import java.util.List;

public class AutoMessageTask extends BukkitRunnable {

    private final CorePlugin plugin;
    private int i;

    public AutoMessageTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.i = 0;
        this.runTaskTimerAsynchronously(plugin, 800L, 800L);
    }

    @Override
    public void run() {
        List<String> messages = this.plugin.getAutoMessageConfiguration().getCustomConfig().getStringList("auto-messages");
        if (messages.isEmpty()) {
            return;
        }

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (!corePlayer.isDisabledSetting(PlayerSettings.AUTO_MESSAGES)) {
                TextUtil.message(player, messages.get(i));
            }

        });
        i++;

        if (i >= messages.size()) {
            i = 0;
        }
    }
}
