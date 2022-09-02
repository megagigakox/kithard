package pl.kithard.core.settings.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.settings.gui.ServerSettingGui;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class ServerSettingsCommand {

    private final CorePlugin plugin;

    public ServerSettingsCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "serversettings",
            aliases = "ss",
            playerOnly = true,
            acceptsExceeded = true,
            permission = "kithard.commands.serversettings"
    )
    public void handle(Player player) {

        new ServerSettingGui(plugin).open(player);

    }
}
