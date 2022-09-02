package pl.kithard.core.guild;

import org.bukkit.Location;

public class GuildRegion {
    private final Location center;
    private int size;

    public GuildRegion(Location location, int size) {
        this.center = location;
        this.size = size;
    }

    public boolean isIn(Location location) {
        int distanceX = Math.abs(location.getBlockX() - this.getX());
        int distanceZ = Math.abs(location.getBlockZ() - this.getZ());

        return distanceX <= this.getSize() && distanceZ <= this.getSize();
    }

    public boolean isInHeart(Location location) {
        int top = 3;
        int down = 2;
        int wall = 4;
        Location clone = this.getCenter().clone();
        return clone.getBlockY() - down <= location.getBlockY() && clone.getBlockY() + top >= location.getBlockY() &&
                location.getBlockX() <= clone.getBlockX() + wall
                && location.getBlockX() >= clone.getBlockX() - wall
                && location.getBlockZ() <= clone.getBlockZ() + wall
                && location.getBlockZ() >= clone.getBlockZ() - wall;
    }

    public Location getCenter() {
        return center;
    }

    public int getX() {
        return  this.center.clone().getBlockX();
    }

    public int getZ() {
        return this.center.clone().getBlockZ();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}