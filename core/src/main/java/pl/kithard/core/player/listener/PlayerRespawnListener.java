package pl.kithard.core.player.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.combat.PlayerCombat;

public class PlayerRespawnListener implements Listener {

    private final CorePlugin plugin;

    public PlayerRespawnListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void handle(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        PlayerCombat playerCombat = corePlayer.getCombat();
        if (playerCombat.hasFight()) {
            playerCombat.setLastAttackTime(0L);
        }

    }

}
