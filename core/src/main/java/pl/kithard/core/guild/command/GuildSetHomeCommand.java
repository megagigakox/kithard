package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildSetHomeCommand {

    private final CorePlugin plugin;

    public GuildSetHomeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g sethome",
            aliases = {"g ustawbaze", "g ustawdom", "g setbase"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_HOME_CREATING)) {
            return;
        }

        if (!guild.getRegion().isIn(player.getLocation())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie możesz ustawic bazy gildyjnej za terenem gildii!");
            return;
        }

        Location location = LocationUtil.toCenter(player.getLocation());
        guild.setHome(location);
        guild.addLog(new GuildLog(
                GuildLogType.OTHER,
                "&f" + player.getName() + " &7ustawil baze gildyjna na kordach: &fx:" + location.getBlockX() + " z:" + location.getBlockZ())
        );
        guild.setNeedSave(true);

        TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie &austawiles nowa lokalizacje &7bazy gildyjnej!");
    }

}
