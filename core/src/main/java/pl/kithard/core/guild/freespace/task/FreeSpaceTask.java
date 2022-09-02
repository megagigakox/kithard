package pl.kithard.core.guild.freespace.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.border.util.BorderUtil;
import pl.kithard.core.guild.GuildCache;
import pl.kithard.core.util.RandomUtil;

public class FreeSpaceTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public FreeSpaceTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 20 * 1000L, 20 * 1000L);
    }

    @Override
    public void run() {
        this.plugin.getFreeSpaceCache().clear();
        do {

            Location location = new Location(Bukkit.getWorld("world"), RandomUtil.getRandInt(-2000, 2000), 70, RandomUtil.getRandInt(-2000, 2000));
            GuildCache guildCache = this.plugin.getGuildCache();
            if (!guildCache.canCreateGuildBySpawnLocation(location)) {
                continue;
            }

            if (!guildCache.canCreateGuildByGuildLocation(location)) {
                continue;
            }

            if (BorderUtil.isBorderNear(location, 120)) {
                continue;
            }

            this.plugin.getFreeSpaceCache().add(location);

        } while (this.plugin.getFreeSpaceCache().values().size() < 52);
    }
}