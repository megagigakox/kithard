package pl.kithard.core.player;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CorePlayerCache  {

    private final Map<UUID, CorePlayer> playersByUuid = new ConcurrentHashMap<>();
    private final Map<String, CorePlayer> playersByName = new ConcurrentHashMap<>();

    public void add(CorePlayer corePlayer) {
        this.playersByUuid.put(corePlayer.getUuid(), corePlayer);
        this.playersByName.put(corePlayer.getName().toLowerCase(), corePlayer);
    }

    public void remove(CorePlayer corePlayer) {
        this.playersByUuid.remove(corePlayer.getUuid());
        this.playersByName.remove(corePlayer.getName().toLowerCase());
    }

    public void updateUsername(CorePlayer corePlayer, String newUsername) {
        this.playersByName.remove(corePlayer.getName());
        this.playersByName.put(newUsername, corePlayer);

        corePlayer.setName(newUsername);
        corePlayer.setNeedSave(true);
    }

    public CorePlayer findByUuid(UUID uuid) {
        return this.playersByUuid.get(uuid);
    }

    public CorePlayer findByPlayer(Player player) {
        return this.findByUuid(player.getUniqueId());
    }

    public CorePlayer findByName(String name) {
        return this.playersByName.get(name.toLowerCase());
    }

    public Collection<CorePlayer> getValues() {
        return this.playersByUuid.values();
    }

}
