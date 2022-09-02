package pl.kithard.core.generator;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;
import pl.kithard.core.api.entity.DatabaseEntity;

@DatabaseEntity(database = "core", collection = "generators")
public class Generator {

    @SerializedName("_id")
    private final Location location;

    public Generator(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
