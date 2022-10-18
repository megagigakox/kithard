package pl.kithard.core.grouptp;

import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class GroupTeleportBorder {

    private final Location centerLocation;
    private final Vector minimumPoint, maximumPoint;
    private final WorldBorder border;

    public GroupTeleportBorder(Location centerLocation) {
        int cuboidSize = 70 / 2;
        this.centerLocation = centerLocation;
        this.minimumPoint = new Vector(centerLocation.getX() - cuboidSize, 0D, centerLocation.getZ() - cuboidSize);
        this.maximumPoint = new Vector(centerLocation.getX() + cuboidSize, 256D, centerLocation.getZ() + cuboidSize);
        this.border = new WorldBorder();
        this.border.setCenter(centerLocation.getX(), centerLocation.getZ());
        this.border.setSize((cuboidSize * 2D) + 2D);
    }

    public WorldBorder getBorder() {
        return border;
    }

    public Location getCenterLocation() {
        return this.centerLocation;
    }

    public boolean isInside(Location location) {
        return location.toVector().isInAABB(this.minimumPoint, this.maximumPoint);
    }

}
