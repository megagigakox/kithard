package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

import java.util.stream.Stream;

@FunnyComponent
public class KickCommand {

    private final CorePlugin plugin;

    public KickCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "kick",
            permission = "kithard.commands.kick",
            acceptsExceeded = true
    )
    public void handle(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(commandSender, "/kick (gracz) (powod)");
            return;
        }

        Player player = this.plugin.getServer().getPlayerExact(args[0]);
        if (player == null) {
            TextUtil.message(commandSender, "&8(&4&l!&8) &cTen gracz jest offline!");
            return;
        }

        String reason = StringUtils.join(args, " ", 1, args.length)
                .replace("&", "");
        player.kickPlayer(TextUtil.color("&cZostales wyrzucony z powodem: \n" + "&4" + reason));

    }
}
