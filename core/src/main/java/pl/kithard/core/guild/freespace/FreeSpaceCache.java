package pl.kithard.core.guild.freespace;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class FreeSpaceCache {

    private final Set<Location> locations = new CopyOnWriteArraySet<>();

    public void add(Location location) {
        this.locations.add(location);
    }

    public void clear() {
        this.locations.clear();
    }

    public Set<Location> values() {
        return new HashSet<>(this.locations);
    }

}
