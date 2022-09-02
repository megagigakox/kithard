package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class FeedCommand {

    private final CorePlugin plugin;

    public FeedCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "feed",
            permission = "kithard.commands.feed",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            player.setFoodLevel(20);
            TextUtil.message(player, "&8[&3&l!&8] &7Twoj glod zostal pomyslnie &bzaspokojony&7!");
            return;
        }

        if (!player.hasPermission("kithard.commands.feed.other")) {
            TextUtil.insufficientPermission(player, "kithard.commands.feed.other");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        target.setFoodLevel(20);
        TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie &3zaspokojono &7glod gracza &b" + target.getName() + "&7!");
    }

}
