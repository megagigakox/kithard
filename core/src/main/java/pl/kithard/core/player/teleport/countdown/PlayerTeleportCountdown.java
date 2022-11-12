package pl.kithard.core.player.teleport.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.teleport.PlayerTeleport;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.util.TitleUtil;

import java.util.concurrent.TimeUnit;

public class PlayerTeleportCountdown extends BukkitRunnable {

    private final CorePlugin plugin;

    public PlayerTeleportCountdown(CorePlugin plugin) {
        this.plugin = plugin;
        super.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player == null) {
                continue;
            }

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (corePlayer == null) {
                continue;
            }

            PlayerTeleport playerTeleport = corePlayer.getTeleport();
            if (playerTeleport != null) {
                int time = (int) TimeUnit.MILLISECONDS.toSeconds(playerTeleport.getTime() - currentTimeMillis);

                if (time > 0) {
                    TitleUtil.title(player, "", "&7Teleportacja nastapi za&8: &f"
                            + TimeUtil.formatTimeMillis(playerTeleport.getTime() - currentTimeMillis), 0, 40, 0);
                    continue;
                }

                this.plugin.getServer()
                        .getScheduler()
                        .runTask(plugin, () -> player.teleport(playerTeleport.getTeleportLocation()));
                TitleUtil.title(player, "", "&aPomyslnie przeteleportowano!", 0, 20, 40);
                corePlayer.setTeleport(null);
            }
        }
    }
}
