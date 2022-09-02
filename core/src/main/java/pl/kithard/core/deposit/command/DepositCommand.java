package pl.kithard.core.deposit.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.deposit.gui.DepositGui;
import pl.kithard.core.player.CorePlayer;

@FunnyComponent
public class DepositCommand {

    private final CorePlugin plugin;

    public DepositCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "schowek",
            aliases = {"depozyt", "depo"},
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, CorePlayer corePlayer) {
        new DepositGui(plugin).open(player, corePlayer);
    }

}
