package pl.kithard.core.guild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.player.listener.PlayerCommandPreprocessListener;
import pl.kithard.core.util.TextUtil;

public class GuildChatListener implements Listener {

    private final static String[] BLOCKED = {
            "home", "sethome", "tpa", "tpaccept", "repair", "crafting", "wb", "workbench", "enderchest", "ec", "gildia dom", "g dom", "gildia baza", "g baza", "g home", "gildia home", "repair all"
    };

    private final CorePlugin plugin;

    public GuildChatListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        Guild guild = this.plugin.getGuildCache().findByPlayer(player);

        if (message.startsWith("!!") && guild != null) {
            event.setCancelled(true);

            String msg = message.replaceFirst("!!", "").replace("&", "");
            guild.sendMessageToOnlineMembers("&8[&9DO SOJUSZY&8] [&9" + guild.getTag() + "&8] &7" + player.getName() + "&8: &f" + msg);

            for (String it : guild.getAllies()) {
                Guild ally = this.plugin.getGuildCache().findByTag(it);

                if (ally != null) {
                    ally.sendMessageToOnlineMembers("&8[&9DO SOJUSZY&8] [&9" + guild.getTag() + "&8] &7" + player.getName() + "&8: &f" + msg);
                }
            }

        } else if (message.startsWith("!") && guild != null) {
            event.setCancelled(true);
            String msg = message.replaceFirst("!", "").replace("&", "");
            guild.sendMessageToOnlineMembers("&8[&bDO GILDII&8] &7" + player.getName() + "&8: &f" + msg);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGuildCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        Guild guild = this.plugin.getGuildCache().findByLocation(player.getLocation());
        if (guild == null) {
            return;
        }

        String cmd = event.getMessage().toLowerCase();
        if (!guild.isMember(player.getUniqueId()) && !player.hasPermission("kithard.guilds.admin")) {
            for (String s : BLOCKED) {
                if (cmd.contains("/" + s)) {
                    event.setCancelled(true);
                    TextUtil.message(player, "&cNie mozesz uzyc tej komendy na terenie nie swojej gildii!");
                    return;
                }
            }
        }

        if (cmd.equals("/tpaccept") && this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.TELEPORTATION_USE)) {
            event.setCancelled(true);
        }
    }
}
