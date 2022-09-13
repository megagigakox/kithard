package pl.kithard.core.player.home;


import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class PlayerHome {

    public static final Map<Integer, String> REQUIRED_RANK = new HashMap<>();

    static {
        REQUIRED_RANK.put(1, "default");
        REQUIRED_RANK.put(2, "vip");
        REQUIRED_RANK.put(3, "svip");
        REQUIRED_RANK.put(4, "sponsor");
        REQUIRED_RANK.put(5, "legenda");
    }

    private final int id;
    private Location location;

    public PlayerHome(int id, Location location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
