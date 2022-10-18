package pl.kithard.core.border.util;

import org.bukkit.Location;

public final class BorderUtil {

    private BorderUtil() {}

    private static int borderXDistance(Location location) {
        return (int)(location.getWorld().getWorldBorder().getSize() / 2.0D - Math.abs(location.getBlockX()));
    }

    private static int borderZDistance(Location location) {
        return (int)location.getWorld().getWorldBorder().getSize() / 2 - Math.abs(location.getBlockZ());
    }

    public static boolean isBorderNear(Location location, int distance) {
        return (borderXDistance(location) <= distance || borderZDistance(location) <= distance);
    }

}
