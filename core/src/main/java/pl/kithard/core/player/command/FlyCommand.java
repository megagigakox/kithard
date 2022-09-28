package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class FlyCommand {

    private final CorePlugin plugin;

    public FlyCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "fly",
            permission = "kithard.commands.fly",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {

        if (args.length < 1) {

            player.setAllowFlight(!player.getAllowFlight());
            TextUtil.message(player, "&8(&3&l!&8) &7Zmieniles tryb latania na: " + (player.getAllowFlight() ? "&awlaczony" : "&cwylaczony"));
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        target.setAllowFlight(!target.getAllowFlight());
        TextUtil.message(player, "&8(&3&l!&8) &7Zmieniles tryb latania dla gracza &b " + target.getName() + " &7na:" + (target.getAllowFlight() ? "&awlaczony" : "&cwylaczony"));


    }

}
