package pl.kithard.core.guild.freespace.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.border.util.BorderUtil;
import pl.kithard.core.util.RandomUtil;

public class FreeSpaceTask extends BukkitRunnable {

    private final CorePlugin plugin;
    private final World world;

    public FreeSpaceTask(CorePlugin plugin, World world) {
        this.plugin = plugin;
        this.world = world;
        this.runTaskTimerAsynchronously(plugin, 0L, 20 * 1000L);
    }

    @Override
    public void run() {
        this.plugin.getFreeSpaceCache().clear();
        do {

            Location location = new Location(this.world, RandomUtil.getRandInt(-1000, 1000), 70, RandomUtil.getRandInt(-1000, 1000));
            if (!this.plugin.getGuildCache().canCreateGuildBySpawnLocation(location)) {
                continue;
            }

            if (!this.plugin.getGuildCache().canCreateGuildByGuildLocation(location)) {
                continue;
            }

            if (BorderUtil.isBorderNear(location, 120)) {
                continue;
            }

            this.plugin.getFreeSpaceCache().add(location);

        } while (this.plugin.getFreeSpaceCache().values().size() < 52);
    }
}