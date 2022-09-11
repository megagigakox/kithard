package pl.kithard.core.player.reward;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class RewardCommand {

    private final CorePlugin plugin;

    public RewardCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "nagroda",
            aliases = "reward",
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new RewardGui(this.plugin).open(player);
    }

}
