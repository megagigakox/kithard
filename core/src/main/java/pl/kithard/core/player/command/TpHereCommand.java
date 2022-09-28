package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class TpHereCommand {

    private final CorePlugin plugin;

    public TpHereCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "tphere",
            aliases = "s",
            permission = "kithard.commands.tphere",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/tphere (gracz)");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        target.teleport(player);
        TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3przeteleportowano &7 gracza &b" + target.getName() + "&7!");
    }

}
