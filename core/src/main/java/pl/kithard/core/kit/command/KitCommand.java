package pl.kithard.core.kit.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.kit.gui.KitGui;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class KitCommand {

    private final CorePlugin plugin;

    public KitCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "kit",
            aliases = "kity",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new KitGui(plugin).open(player);
    }
}
