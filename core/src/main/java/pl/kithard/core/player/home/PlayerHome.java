package pl.kithard.core.player.home;


import org.bukkit.Location;

public class PlayerHome {

    private final int id;
    private final String requiredRank;
    private Location location;

    public PlayerHome(int id, String requiredRank) {
        this.id = id;
        this.requiredRank = requiredRank;
    }

    public int getId() {
        return id;
    }

    public String getRequiredRank() {
        return requiredRank;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "PlayerHome{" +
                "id=" + id +
                ", requiredRank='" + requiredRank + '\'' +
                ", location=" + location +
                '}';
    }
}
