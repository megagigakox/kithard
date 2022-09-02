package pl.kithard.queue.util;

import org.bukkit.Location;

public final class LocationUtil {

    private LocationUtil() {}

    public static Location toCenter(Location location) {
        Location centerLoc = location.clone();
        centerLoc.setX((double)location.getBlockX() + 0.5D);
        centerLoc.setZ((double)location.getBlockZ() + 0.5D);
        return centerLoc;
    }


}
