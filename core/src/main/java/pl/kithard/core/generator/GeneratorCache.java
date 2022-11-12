package pl.kithard.core.generator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.kithard.core.CorePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GeneratorCache {
    private final CorePlugin plugin;

    private final Map<Location, Generator> generators = new ConcurrentHashMap<>();

    public GeneratorCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void add(Generator generator) {
        this.generators.put(generator.getLocation(), generator);
    }

    public void remove(Generator generator) {
        this.generators.remove(generator.getLocation());
    }

    public void regen(Generator generator) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {

            Block block = generator.getLocation()
                    .getWorld()
                    .getBlockAt(generator.getLocation());
            block.setType(Material.STONE);

        }, (long)(20 * 1.5));
    }

    public Generator findByLocation(Location location) {
        return this.generators.get(location);
    }
}
