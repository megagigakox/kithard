package pl.kithard.core.guild.periscope.task;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;

public class GuildPeriscopeTask extends BukkitRunnable {

    private final CorePlugin plugin;
    private final Location oldLocation, newLocation;
    private final Player player;
    private final GuildMember guildMember;

    public GuildPeriscopeTask(CorePlugin plugin, Location oldLocation, Location location, Player player, GuildMember guildMember) {
        this.plugin = plugin;
        this.oldLocation = oldLocation;
        this.newLocation = location;
        this.player = player;
        this.guildMember = guildMember;
        this.runTaskTimer(plugin, 20L, 20L);
    }


    @Override
    public void run() {
        if (player == null) {
            cancel();
            return;
        }

        if (player.isSneaking()) {
            guildMember.setPeriscope(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(oldLocation);
            this.plugin.getActionBarNoticeCache().remove(player.getUniqueId(), ActionBarNoticeType.PERISCOPE);
            cancel();
            return;
        }

        player.teleport(newLocation);
    }
}
