package pl.kithard.core.guild.periscope;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.guild.periscope.task.GuildPeriscopeTask;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;

public class GuildPeriscope {

    private final CorePlugin plugin;

    public GuildPeriscope(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void start(GuildMember guildMember, Guild guild) {
        Player player = Bukkit.getPlayer(guildMember.getUuid());

        Location newLocation = guild.getRegion()
                .getCenter()
                .clone()
                .add(0.0, 100, 0.0);
        newLocation.setYaw(-180);
        newLocation.setPitch(90);
        Location oldLocation = player.getLocation();
        player.teleport(newLocation);
        player.setGameMode(GameMode.SPECTATOR);
        guildMember.setPeriscope(true);
        this.plugin.getActionBarNoticeCache().add(
                player.getUniqueId(),
                ActionBarNotice.builder()
                        .type(ActionBarNoticeType.PERISCOPE)
                        .text("&2&l✔ &aJestes w trybie obserwatora! Aby wyjsc przytrzymaj &2&lSHIFT &2&l✔")
                        .build()
        );
        new GuildPeriscopeTask(this.plugin, oldLocation, newLocation, player, guildMember);
    }


}
