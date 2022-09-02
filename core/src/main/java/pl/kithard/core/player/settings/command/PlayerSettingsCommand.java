package pl.kithard.core.player.settings.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.settings.gui.PlayerSettingsGui;

@FunnyComponent
public class PlayerSettingsCommand {

    private final CorePlugin plugin;

    public PlayerSettingsCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "chatcontrol",
            aliases = {"chatsettings", "cc", "settings", "ustawienia"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new PlayerSettingsGui(plugin).open(player);
    }

}
