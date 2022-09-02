package pl.kithard.core.guild.freespace.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.freespace.gui.FreeSpaceGui;

@FunnyComponent
public class FreeSpaceCommand {

    private final CorePlugin plugin;

    public FreeSpaceCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g wolnemiejsce",
            aliases = "g freespace",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player) {

        new FreeSpaceGui(plugin).open(player);

    }
}
