package pl.kithard.core.effect.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.effect.gui.CustomEffectGui;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class CustomEffectCommand {

    private final CorePlugin plugin;

    public CustomEffectCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "efekty",
            aliases = {"efekt", "eg"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new CustomEffectGui(plugin).open(player);
    }

}
