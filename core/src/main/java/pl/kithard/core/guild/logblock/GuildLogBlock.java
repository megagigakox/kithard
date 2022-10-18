package pl.kithard.core.guild.logblock;

import org.bukkit.Location;
import org.bukkit.Material;

public class GuildLogBlock {

    private final GuildLogBlockType type;
    private final String player;
    private final Material material;
    private final byte data;
    private final Location location;
    private final long date;

    public GuildLogBlock(GuildLogBlockType type, String player, Material material, byte data, Location location, long date) {
        this.type = type;
        this.player = player;
        this.material = material;
        this.data = data;
        this.location = location;
        this.date = date;
    }

    public GuildLogBlockType getType() {
        return type;
    }

    public String getPlayer() {
        return player;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    public Location getLocation() {
        return location;
    }

    public long getDate() {
        return date;
    }
}
