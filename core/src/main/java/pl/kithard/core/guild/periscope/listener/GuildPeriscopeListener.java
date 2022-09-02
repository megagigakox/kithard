package pl.kithard.core.guild.periscope.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;

public class GuildPeriscopeListener implements Listener {

    private final CorePlugin plugin;

    public GuildPeriscopeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("kithard.commands.gamemode")) {
            return;
        }

        if (player.getGameMode() == GameMode.SPECTATOR && event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Guild guild = this.plugin.getGuildCache().findByPlayer(player);
        if (guild == null) {
            return;
        }
        GuildMember guildMember = guild.findMemberByUuid(player.getUniqueId());

        if (guildMember == null) {
            return;
        }

        if (!guildMember.isPeriscope()) {
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        guildMember.setPeriscope(false);
        this.plugin.getActionBarNoticeCache().remove(guildMember.getUuid(), ActionBarNoticeType.PERISCOPE);
    }
}
