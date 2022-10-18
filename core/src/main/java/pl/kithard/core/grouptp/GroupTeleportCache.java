package pl.kithard.core.grouptp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GroupTeleportCache {

    private final Map<Player, GroupTeleport> groupTeleports = new HashMap<>();

    public GroupTeleport find(Player player) {
        return this.groupTeleports.get(player);
    }

    public void add(GroupTeleport groupTeleport) {
        groupTeleport.getPlayers().forEach(player -> this.groupTeleports.put(player, groupTeleport));
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
