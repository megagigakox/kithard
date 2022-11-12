package pl.kithard.core.grouptp;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupTeleportCache {

    private final Map<Player, GroupTeleport> groupTeleports = new ConcurrentHashMap<>();

    public GroupTeleport find(Player player) {
        return this.groupTeleports.get(player);
    }

    public void add(GroupTeleport groupTeleport) {
        groupTeleport.getPlayers().forEach(player -> this.groupTeleports.put(player, groupTeleport));
    }

    public boolean canCreate(Location location) {
        final int minDistance = 70 + 80;
        for (GroupTeleport groupTeleport : this.groupTeleports.values()) {
            GroupTeleportBorder border = groupTeleport.getGroupTeleportBorder();
            if (border.isInside(location) || border.getCenterLocation().distanceSquared(location) <= minDistance) {
                return false;
            }
        }

        return true;
    }


    public void remove(GroupTeleport groupTeleport) {
        groupTeleport.getPlayers().forEach(player -> {
            this.groupTeleports.remove(player);
            groupTeleport.getPlayers().remove(player);
        });
    }

    public void remove(Player player, GroupTeleport groupTeleport) {
        groupTeleport.getPlayers().remove(player);
        this.groupTeleports.remove(player);
    }

    public void prepareWorld() {
        World newWorld = Bukkit.createWorld(new WorldCreator("gtp")
                .type(WorldType.FLAT));
        newWorld.setAnimalSpawnLimit(0);
        newWorld.setAmbientSpawnLimit(0);
        newWorld.setMonsterSpawnLimit(0);
        newWorld.setStorm(false);
        newWorld.setThundering(false);
        newWorld.setAutoSave(false);
    }

}
