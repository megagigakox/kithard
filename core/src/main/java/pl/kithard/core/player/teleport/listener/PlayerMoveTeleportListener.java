package pl.kithard.core.player.teleport.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

public class PlayerMoveTeleportListener implements Listener {

    private final CorePlugin plugin;

    public PlayerMoveTeleportListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(event.getPlayer());

        if (corePlayer.getTeleport() != null) {
            corePlayer.setTeleport(null);
        }

    }
}
