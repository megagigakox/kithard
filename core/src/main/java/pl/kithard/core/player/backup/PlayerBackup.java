package pl.kithard.core.player.backup;

import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.util.MathUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerBackup extends DatabaseEntry {

    private final UUID id;
    private final String playerName;
    private final PlayerBackupType type;
    private final long createTime;
    private final String killer;
    private final ItemStack[] inventory, armor;
    private final int ping, lostPoints;
    private final float tps;
    private final Map<Long, String> adminsRestored;

    public PlayerBackup(UUID id, String playerName, PlayerBackupType type, long createTime, String killer, ItemStack[] inventory, ItemStack[] armor, int ping, int lostPoints, float tps, Map<Long, String> adminsRestored) {
        this.id = id;
        this.playerName = playerName;
        this.type = type;
        this.createTime = createTime;
        this.killer = killer;
        this.inventory = inventory;
        this.armor = armor;
        this.ping = ping;
        this.lostPoints = lostPoints;
        this.tps = tps;
        this.adminsRestored = adminsRestored;
    }

    public PlayerBackup(String playerName, PlayerBackupType type, String killer, ItemStack[] inventory, ItemStack[] armor, int ping, int lostPoints) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.playerName = playerName;
        this.killer = killer;
        this.inventory = inventory;
        this.armor = armor;
        this.lostPoints = lostPoints;
        this.tps = (float) MathUtil.round(MinecraftServer.getServer().recentTps[0], 2);
        this.createTime = System.currentTimeMillis();
        this.ping = ping;

        this.adminsRestored = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerBackupType getType() {
        return type;
    }

    public String getKiller() {
        return killer;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public int getLostPoints() {
        return lostPoints;
    }

    public long getCreateTime() {
        return createTime;
    }

    public int getPing() {
        return ping;
    }

    public float getTps() {
        return tps;
    }

    public Map<Long, String> getAdminsRestored() {
        return adminsRestored;
    }
}
