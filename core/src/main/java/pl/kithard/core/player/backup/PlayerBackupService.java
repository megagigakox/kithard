package pl.kithard.core.player.backup;

import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

public class PlayerBackupService {

    private final CorePlugin plugin;

    public PlayerBackupService(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void restoreBackup(Player player, Player toRestore, PlayerBackup playerBackup, boolean withPoints) {
        if (!withPoints) {
            toRestore.getInventory().setArmorContents(playerBackup.getArmor());
            toRestore.getInventory().setContents(playerBackup.getInventory());
        } else {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(toRestore);

            toRestore.getInventory().setArmorContents(playerBackup.getArmor());
            toRestore.getInventory().setContents(playerBackup.getInventory());
            corePlayer.addPoints(playerBackup.getLostPoints());

        }

        TextUtil.message(toRestore, "&8[&3&l!&8] &7Twoj ekwipunek zostal cofniety do &f" +
                TimeUtil.formatTimeMillisToDate(playerBackup.getDate()) +
                " &7przez &b" + player.getName());
        TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie cofnales ekwipunek do &f" +
                TimeUtil.formatTimeMillisToDate(playerBackup.getDate()) +
                " &7graczowi &b" + toRestore.getName());


        playerBackup.getAdminsRestored().put(System.currentTimeMillis(), player.getName());
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getMongoService().save(playerBackup));
    }

}
