package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

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
        corePlayer.setIncognito(!corePlayer.isIncognito());
        TextUtil.message(corePlayer.source(), "&8(&3&l!&8) &7Incognito zostalo " + (corePlayer.isIncognito() ? "&awlaczone" : "&cwylaczone") + "&7!");
        this.plugin.getPlayerNameTagService().updateDummy(corePlayer);

    }

}
