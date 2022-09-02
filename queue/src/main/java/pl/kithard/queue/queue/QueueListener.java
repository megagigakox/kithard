package pl.kithard.queue.queue;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.kithard.queue.QueuePlugin;
import pl.kithard.queue.util.LocationUtil;

public class QueueListener implements Listener {

    private final QueuePlugin plugin;

    public QueueListener(QueuePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        this.plugin.getServer().getOnlinePlayers().forEach(
                it -> {
                    player.hidePlayer(it);
                    it.hidePlayer(player);
                });

        player.teleport(LocationUtil.toCenter(new Location(Bukkit.getWorld("world_the_end"), 0, 250, 0)));
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGameMode(GameMode.ADVENTURE);
        player.setWalkSpeed(0F);
        player.setFlySpeed(0F);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        QueuePlayer queuePlayer = this.plugin.getQueuePlayerCache().findPlayer(event.getPlayer().getUniqueId());
        if (queuePlayer != null) {
            this.plugin.getQueuePlayerCache().remove(queuePlayer);
        }
    }
}
