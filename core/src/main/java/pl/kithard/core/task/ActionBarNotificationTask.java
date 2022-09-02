package pl.kithard.core.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.util.ActionBarUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TimeUtil;
import pl.kithard.core.CorePlugin;

public class ActionBarNotificationTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public ActionBarNotificationTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 13L, 13L);
    }

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();

        for (Player player : Bukkit.getOnlinePlayers()) {

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            ServerSettings serverSettings = this.plugin.getServerSettings();
            String message = "";

            if (corePlayer.isVanish()) {
                message += "&3&lVanish";
            }

            PlayerCombat playerCombat = corePlayer.getCombat();
            if (playerCombat != null && playerCombat.hasFight()) {
                if (!message.equals("")) {
                    message += " &8| ";
                }
                message += "&b&lAntylogout &8(&f" + (TimeUtil.formatTimeMillis(playerCombat.getLastAttackTime() - currentTimeMillis) + "&8)");
            }

            if (serverSettings.getTurboDrop() > currentTimeMillis) {
                if (!message.equals("")) {
                    message += " &8| ";
                }
                message += "&b&lTurboDrop &8(&f" + TimeUtil.formatTimeMillis(serverSettings.getTurboDrop() - currentTimeMillis) + "&8)";
            }

            Guild guildByLocation = this.plugin.getGuildCache().findByLocation(player.getLocation());

            if (guildByLocation != null) {
                if (!message.equals("")) {
                    message += " &8| ";
                }

                Guild playerGuild = this.plugin.getGuildCache().findByPlayer(player);
                double distance = MathUtil.round(LocationUtil.distance(player.getLocation(), guildByLocation.getRegion().getCenter().clone()), 2);

                if (guildByLocation != playerGuild) {
                    if (playerGuild != null && playerGuild.getAllies().contains(guildByLocation.getTag())) {
                        message = message + "&7Teren gildii &8[&6" + guildByLocation.getTag() + "&8] &b" + distance + "m";
                    }
                    else {
                        message = message + "&7Teren gildii &8[&c" + guildByLocation.getTag() + "&8] &b" + distance + "m";
                    }
                }
                else {
                    message = message + "&7Teren gildii &8[&a" + guildByLocation.getTag() + "&8] &b" + distance + "m";
                }
            }

            if (!message.isEmpty()) {
                ActionBarUtil.actionBar(player, message);
            }

        }

    }
}
