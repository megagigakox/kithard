package pl.kithard.core.guild.panel.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.permission.GuildPermission;

@FunnyComponent
public class GuildPanelCommand {

    private final CorePlugin plugin;

    public GuildPanelCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g panel",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.PANEL_ACCESS)) {
            return;
        }

        new GuildPanelGui(plugin).openPanel(player, guild);

    }

}
