package pl.kithard.core.player.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TitleUtil;

public class PlayerMoveListener implements Listener {

    private final CorePlugin plugin;

    public PlayerMoveListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom(), to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
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

        PlayerCombat combat = corePlayer.getCombat();
        if (combat.hasFight() && !player.hasPermission("kithard.antilogout.bypass")) {
            if (player.getWorld().getName().equals("world")) {
                if (LocationUtil.loc(61, -61, -61, 61, to)) {
                    event.setCancelled(true);

                    Location l = player.getWorld().getSpawnLocation().subtract(player.getLocation());
                    double distance = player.getLocation().distance(player.getWorld().getSpawnLocation());

                    Vector vector = l.toVector().add(new Vector(0, 5, 0)).multiply(1.25 / distance);
                    player.setVelocity(vector.multiply(-1.5));
                }
            }
        }

//        if (LocationUtil.isInSpawn(player.getLocation())) {
//            if (player.hasPermission("kithard.flyonspawn")) {
//
//                if (player.getAllowFlight()) {
//                    return;
//                }
//
//                player.setAllowFlight(true);
//                TextUtil.message(player, "&7Posiadasz range &bpremium&7, aktywowano latanie na spawnie!");
//
//            }
//
//        } else if (!LocationUtil.isInSpawn(player.getLocation()) &&
//                player.getGameMode() != GameMode.CREATIVE && !player.hasPermission("kithard.commands.fly")) {
//
//            player.setAllowFlight(false);
//
//        }

    }

}
