package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public class WorkbenchCommand {

    @FunnyCommand(
            name = "workbench",
            aliases = {"wb", "crafting"},
            permission = "kithard.commands.workbench",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {

        player.openWorkbench(null, true);

    }

}
