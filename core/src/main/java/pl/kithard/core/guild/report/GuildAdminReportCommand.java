package pl.kithard.core.guild.report;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class GuildAdminReportCommand {

    private final CorePlugin plugin;

    public GuildAdminReportCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "ga zgloszenia",
            aliases = "ga reports",
            playerOnly = true,
            permission = "kithard.commands.guildadmin"
    )
    public void handle(Player player) {
        new GuildReportGui(this.plugin).open(player);
    }
}
