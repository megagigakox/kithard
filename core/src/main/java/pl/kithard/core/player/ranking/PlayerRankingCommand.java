package pl.kithard.core.player.ranking;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class PlayerRankingCommand {

    private final CorePlugin plugin;

    public PlayerRankingCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "topki",
            aliases = {"topka", "top"},
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new PlayerRankingGui(plugin).open(player);
    }
}
