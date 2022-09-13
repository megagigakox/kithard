package pl.kithard.core.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import pl.kithard.core.CoreConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LocationUtil {

    private LocationUtil() {}

    public static double distance(Location first, Location second) {
        return Math.max(Math.abs(first.getX() - second.getX()), Math.abs(first.getZ() - second.getZ()));
    }

    public static boolean isInSpawn(Location location) {
        return distance(location, location.getWorld().getSpawnLocation()) < CoreConstants.SPAWN_AREA_DISTANCE;
    }

    public static boolean isInSpawn(Location location, int size) {
        return distance(location, location.getWorld().getSpawnLocation()) < size;
    }

    public static List<Player> getPlayersInRadius(Location location, int size, Material material) {
        List<Player> players = new ArrayList<>();
        for (Player player : location.getWorld().getPlayers()) {
            if (location.distance(player.getLocation()) <= size) {

                Block block = player.getLocation().getBlock();
                Block relative = block.getRelative(BlockFace.DOWN);

                if (!block.getType().equals(material) && !relative.getType().equals(material)) {
                    continue;
                }

                players.add(player);
                if (players.size() == 2) {
                    return players;
                }
            }
        }
        return players;
    }

    public static Location getRadnomLocation() {
        World world = Bukkit.getWorld("world");
        int x = RandomUtil.getRandInt(-(int) (world.getWorldBorder().getSize() / 2), (int) (world.getWorldBorder().getSize() / 2));
        int z = RandomUtil.getRandInt(-(int) (world.getWorldBorder().getSize() /2), (int) (world.getWorldBorder().getSize() / 2));
        double y = (world.getHighestBlockYAt(x, z) + 1.5F);
        return new Location(world, x, y, z);
    }

    public static boolean loc(int minX, int maxX, int minZ, int maxZ, Location l) {
        double[] dim = { minX, maxX };
        Arrays.sort(dim);
        if (l.getX() >= dim[1] || l.getX() <= dim[0]) {
            return false;
        }
        dim[0] = minZ;
        dim[1] = maxZ;
        Arrays.sort(dim);
        return l.getZ() < dim[1] && l.getZ() > dim[0];
    }

    public static Location toCenter(Location location) {
        Location centerLoc = location.clone();
        centerLoc.setX((double)location.getBlockX() + 0.5D);
        centerLoc.setY((double)location.getBlockY() + 0.5D);
        centerLoc.setZ((double)location.getBlockZ() + 0.5D);
        return centerLoc;
    }


}
