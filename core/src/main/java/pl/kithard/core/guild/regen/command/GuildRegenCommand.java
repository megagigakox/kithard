package pl.kithard.core.guild.regen.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.guild.regen.gui.GuildRegenGui;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

@FunnyComponent
public class GuildRegenCommand {

    private final CorePlugin plugin;

    public GuildRegenCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g regeneruj",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {
        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_REGEN_ACCESS)) {
            return;
        }

        new GuildRegenGui(plugin).open(player, guild);

    }

}