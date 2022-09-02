package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class InvseeCommand {

    private final CorePlugin plugin;

    public InvseeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "invsee",
            aliases = {"oi", "openinventory", "open", "inv"},
            playerOnly = true,
            acceptsExceeded = true,
            completer = "online-players:5",
            permission = "kithard.commands.invsee"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/invsee (gracz)");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        player.openInventory(target.getInventory());
    }

}
