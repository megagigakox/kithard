package pl.kithard.core.grouptp;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupTeleport {

    private final GroupTeleportBorder groupTeleportBorder;
    private final Location location;
    private final List<Player> players = new ArrayList<>();
    private long closeCountdownTime = 0L;

    public GroupTeleport(Location location) {
        this.groupTeleportBorder = new GroupTeleportBorder(location);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void prepareBorder() {

        WorldBorder worldBorder = this.groupTeleportBorder.getBorder();
        this.players.forEach(player -> {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(
                    new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(
                    new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE));
        });

    }

    public GroupTeleportBorder getGroupTeleportBorder() {
        return groupTeleportBorder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public long getCloseCountdownTime() {
        return closeCountdownTime;
    }

    public void setCloseCountdownTime(long closeCountdownTime) {
        this.closeCountdownTime = closeCountdownTime;
    }
}
