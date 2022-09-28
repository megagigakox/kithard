package pl.kithard.core.guild.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

public class GuildExpireTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public GuildExpireTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 20 * 900, 20 * 900);
    }

    @Override
    public void run() {

        long currentTimeMillis = System.currentTimeMillis();
        for (Guild guild : this.plugin.getGuildCache().getValues()) {

            if (guild.getExpireTime() < currentTimeMillis) {
                this.plugin.getGuildFactory().delete(guild);
                Bukkit.broadcastMessage(TextUtil.color(" &7Gildia &8[&b" + guild.getTag() + "&8] &7wygasla!"));
                Bukkit.broadcastMessage(TextUtil.color(" &7Jej kordynaty to: &7X: &f" + guild.getRegion().getX() + "&8, &7Z: &f" + guild.getRegion().getZ()));
            }

        }
    }
}
