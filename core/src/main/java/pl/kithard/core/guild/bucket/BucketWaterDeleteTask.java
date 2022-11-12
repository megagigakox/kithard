package pl.kithard.core.guild.bucket;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BucketWaterDeleteTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public BucketWaterDeleteTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20L);
    }

    @Override
    public void run() {
        long currentTimeMilis = System.currentTimeMillis();
        Map<Location, Map.Entry<UUID, Long>> cache = BucketInteractionListener.WATER_CACHE;

        for (Map.Entry<Location, Map.Entry<UUID, Long>> entry : cache.entrySet()) {

            Map.Entry<UUID, Long> secondEntry = entry.getValue();
            Location location = entry.getKey();
            Guild guild = this.plugin.getGuildCache().findByLocation(location);
            if (guild == null || guild.isMember(secondEntry.getKey())) {
                continue;
            }

            long timeStamp = secondEntry.getValue();
            if (timeStamp + TimeUnit.SECONDS.toMillis(15) <= currentTimeMilis) {

                cache.remove(location);
                this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {

                    Block block = location.getBlock();
                    if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
                        block.setType(Material.AIR);
                    }

                });
            }

        }

    }
}
