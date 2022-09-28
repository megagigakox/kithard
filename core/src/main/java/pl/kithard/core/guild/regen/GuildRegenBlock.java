package pl.kithard.core.guild.regen;

import org.bukkit.Location;
import org.bukkit.Material;

public class GuildRegenBlock {

    private final String guild;

    private final Location location;
    private final Material material;
    private final byte data;

    public GuildRegenBlock(String guild, Location location, Material material, byte data) {
        this.guild = guild;
        this.location = location;
        this.material = material;
        this.data = data;
    }

    public String getGuild() {
        return guild;
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