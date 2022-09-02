package pl.kithard.core.guild.regen;

import org.bukkit.Location;
import org.bukkit.Material;

public class RegenBlock {

    private final Location location;
    private final Material material;
    private final byte data;

    public RegenBlock(Location location, Material material, byte data) {
        this.location = location;
        this.material = material;
        this.data = data;
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }
}