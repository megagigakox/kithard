package pl.kithard.core.player.teleport;

import org.bukkit.Location;

public class PlayerTeleport {

    private Location teleportLocation;
    private long time;

    public boolean isTeleport() {
        return this.time > System.currentTimeMillis();
    }

    public Location getTeleportLocation() {
        return teleportLocation;
    }

    public void setTeleportLocation(Location teleportLocation) {
        this.teleportLocation = teleportLocation;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
