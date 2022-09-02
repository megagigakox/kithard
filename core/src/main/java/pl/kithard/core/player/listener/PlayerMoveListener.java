package pl.kithard.core.player.listener;

import org.bukkit.GameMode;
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
import pl.kithard.core.util.TextUtil;

public class PlayerMoveListener implements Listener {

    private final CorePlugin plugin;

    public PlayerMoveListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Location locFrom = event.getFrom(), locTo = event.getTo();
        if (locFrom.getBlockX() == locTo.getBlockX() && locFrom.getBlockZ() == locTo.getBlockZ()) {
            return;
        }

        Player player = event.getPlayer();

        if (LocationUtil.isInSpawn(player.getLocation(), 55)) {
            if (player.hasPermission("kithard.flyonspawn")) {

                if (player.getAllowFlight()) {
                    return;
                }

                player.setAllowFlight(true);
                TextUtil.message(player, "&7Posiadasz range &bpremium&7, aktywowano latanie na spawnie!");

            }

        } else if (!LocationUtil.isInSpawn(player.getLocation(), 55) &&
                player.getGameMode() != GameMode.CREATIVE && !player.hasPermission("kithard.commands.fly")) {

            player.setAllowFlight(false);

        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        PlayerCombat u = corePlayer.getCombat();

        if (u.hasFight() && !player.hasPermission("kithard.antilogout.bypass")) {
            Location to = event.getTo();
            Location from = event.getFrom();
            if ((from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) && LocationUtil.loc(61, -61, -61, 61, to)) {
                event.setCancelled(true);

                Location l = player.getWorld().getSpawnLocation().subtract(player.getLocation());
                double distance = player.getLocation().distance(player.getWorld().getSpawnLocation());

                Vector vector = l.toVector().add(new Vector(0, 5, 0)).multiply(1.25 / distance);
                player.setVelocity(vector.multiply(-1.5));
            }
        }
    }

}
