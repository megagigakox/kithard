package pl.kithard.core.player.teleport.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TitleUtil;

public class PlayerMoveTeleportListener implements Listener {

    private final CorePlugin plugin;

    public PlayerMoveTeleportListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location locFrom = event.getFrom(), locTo = event.getTo();
        if (locFrom.getBlockX() == locTo.getBlockX() && locFrom.getBlockZ() == locTo.getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        if (corePlayer.getTeleport() != null) {
            corePlayer.setTeleport(null);
            TitleUtil.title(
                    player,
                    "&4Ruszyles sie!",
                    "&cTeleportacja zostala przerwana.",
                    20, 30, 20
            );
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(event.getPlayer());

        if (corePlayer.getTeleport() != null) {
            corePlayer.setTeleport(null);
        }

    }
}
