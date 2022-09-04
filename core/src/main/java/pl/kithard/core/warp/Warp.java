package pl.kithard.core.warp;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.kithard.core.api.database.entity.DatabaseEntity;

@DatabaseEntity(database = "core", collection = "warps")
public class Warp {

    @SerializedName("_id")
    private final String name;

    private final Location location;
    private final Material icon;

    public Warp(String name, Location location, Material icon) {
        this.name = name;
        this.location = location;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public Location getLocation() {
        return location;
    }
}
