package pl.kithard.core.player.backup;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

public class PlayerBackupFactory {

    private final CorePlugin plugin;

    public PlayerBackupFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public PlayerBackup create(Player player, PlayerBackupType type, String killer, int lostPoints) {

        return new PlayerBackup(
                player.getName(),
                type,
                killer,
                player.getInventory().getContents(),
                player.getInventory().getArmorContents(),
                ((CraftPlayer)player).getHandle().ping,
                lostPoints);
    }

}
