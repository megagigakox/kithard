package pl.kithard.core.achievement;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class AchievementCommand {

    private final CorePlugin plugin;

    public AchievementCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "osiagniecia",
            aliases = {"os", "achievement", "osiagniecie", "achievements"},
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new AchievementGui(this.plugin).open(player);
    }
}
