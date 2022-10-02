package pl.kithard.core.generator;

import org.bukkit.Location;

public class Generator {

    private final Location location;

    public Generator(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
