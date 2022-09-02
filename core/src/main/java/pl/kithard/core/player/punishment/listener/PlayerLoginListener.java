package pl.kithard.core.player.punishment.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.punishment.type.Ban;
import pl.kithard.core.player.punishment.type.BanIP;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TimeUtil;

public class PlayerLoginListener implements Listener {

    private final CorePlugin plugin;

    public PlayerLoginListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {

        Player player = event.getPlayer();
        Ban ban = this.plugin.getPunishmentCache().findBan(player.getName());
        if (ban != null) {

            if (ban.getTime() != 0L && ban.getTime() <= System.currentTimeMillis()) {
                this.plugin.getPunishmentFactory().reassignBanFromPlayer(player.getName());
                return;
            }

            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, TextUtil.color(
                    "\n&cZostales zbanowany przez &4" + ban.getAdmin() +
                    "\n&cPowod: &4" + ban.getReason() +
                    "\n&cWygasa: &4" + ((ban.getTime() == 0L) ? "Nigdy!" : ("&cza &4" + TimeUtil.formatTimeMillis(ban.getTime() - System.currentTimeMillis())))));
        }

        else {

            BanIP banIP = this.plugin.getPunishmentCache().findBanIP(event.getAddress().getHostAddress());
            if (banIP != null) {

                event.disallow(PlayerLoginEvent.Result.KICK_BANNED, TextUtil.color(
                        "&cTwoje ip zostalo zbanowane przez &4" + banIP.getAdmin() +
                                "\n&cPowod: &4" + banIP.getReason() +
                                "\n&cWygasa: &4Nigdy!"
                ));


            }

        }

    }
}
