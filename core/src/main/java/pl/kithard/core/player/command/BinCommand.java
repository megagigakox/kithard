package pl.kithard.core.player.command;

import dev.triumphteam.gui.guis.Gui;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class BinCommand {

    @FunnyCommand(
            name = "kosz",
            aliases = {"smietnik"},
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player) {
        Gui gui = Gui.gui()
                .rows(6)
                .title(TextUtil.component("&3&lKosz"))
                .create();

        gui.open(player);
    }
}
