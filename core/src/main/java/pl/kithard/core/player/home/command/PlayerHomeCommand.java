package pl.kithard.core.player.home.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.player.home.gui.PlayerHomeGui;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class PlayerHomeCommand {

    private final CorePlugin plugin;

    public PlayerHomeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "home",
            playerOnly = true,
            aliases = "sethome",
            acceptsExceeded = true
    )
    public void home(Player player) {
        if (player.getWorld().getName().equals("gtp")) {
            return;
        }
        new PlayerHomeGui(plugin).open(player);
    }


}
