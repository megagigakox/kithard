package pl.kithard.core.drop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.drop.gui.DropGui;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class DropCommand {

    private final CorePlugin plugin;

    public DropCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "drop",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player) {
        new DropGui(plugin).open(player);
    }

}
