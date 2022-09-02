package pl.kithard.core.guild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class GuildChatListener implements Listener {

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
}
