package pl.kithard.core.player.incognito.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.incognito.gui.PlayerIncognitoGui;

@FunnyComponent
public class IncognitoCommand {

    private final CorePlugin plugin;

    public IncognitoCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "incognito",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(CorePlayer corePlayer) {
        new PlayerIncognitoGui(plugin).open(corePlayer.source(), corePlayer);

    }

}
