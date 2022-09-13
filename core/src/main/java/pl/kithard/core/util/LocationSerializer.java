package pl.kithard.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class LocationSerializer {

    private LocationSerializer() {}

    public static String serialize(Location location) {
        return location.getWorld() + ":" + location.getX() + ":" + location.getY() + ":" + location.getY() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    public Location deserialize(String locationFromText) {
        String[] split = locationFromText.split(":");
        return new Location(
                Bukkit.getWorld(split[0]),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5])
        );
    }

}
