package pl.kithard.core.player.backup;

import com.google.gson.annotations.SerializedName;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.api.database.entity.DatabaseEntity;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.util.MathUtil;

import java.util.*;

@DatabaseEntity(database = "core", collection = "player-backups")
public class PlayerBackup extends DatabaseEntry {

    @SerializedName("_id")
    private final UUID uuid;

    private final UUID playerUuid;
    private final PlayerBackupType type;
    private final long date;
    private final String killer;
    private final ItemStack[] inventory, armor;
    private final int ping, lostPoints;
    private final double tps;
    private final Map<Long, String> adminsRestored;

    public PlayerBackup(UUID playerUuid, PlayerBackupType type, String killer, ItemStack[] inventory, ItemStack[] armor, int ping, int lostPoints) {
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.playerUuid = playerUuid;
        this.killer = killer;
        this.inventory = inventory;
        this.armor = armor;
        this.lostPoints = lostPoints;
        this.tps = MathUtil.round(MinecraftServer.getServer().recentTps[0], 2);
        this.date = System.currentTimeMillis();
        this.ping = ping;

        this.adminsRestored = new HashMap<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
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

    public long getDate() {
        return date;
    }

    public int getPing() {
        return ping;
    }

    public double getTps() {
        return tps;
    }

    public Map<Long, String> getAdminsRestored() {
        return adminsRestored;
    }
}
