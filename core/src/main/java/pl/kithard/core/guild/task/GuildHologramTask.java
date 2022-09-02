package pl.kithard.core.guild.task;

import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class GuildHologramTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public GuildHologramTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(this.plugin, 500L, 500L);
    }

    @Override
    public void run() {
        for (Guild guild : this.plugin.getGuildCache().getValues()) {
            this.plugin.getGuildFactory().updateHologram(guild);
        }
    }
}
