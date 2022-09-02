package pl.kithard.core.guild.command.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildAdminTeleportCommand {

    private final CorePlugin plugin;

    public GuildAdminTeleportCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "ga tp",
            acceptsExceeded = true,
            playerOnly = true,
            permission = "kithard.commands.guildadmin.tp"
    )
    public void handle(Player player, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/ga tp (tag)");
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            return;
        }

        player.teleport(guild.getRegion().getCenter().clone().add(0, 100, 0));

    }

}
