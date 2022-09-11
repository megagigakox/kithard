package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TitleUtil;

@FunnyComponent
public class BroadcastCommand {

    @FunnyCommand(
            name = "broadcast",
            aliases = "bc",
            permission = "kithard.commands.broadcast",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/broadcast (title/chat)");
            return;
        }

        String message = TextUtil.color(StringUtils.join(args, " ", 1, args.length));
        switch (args[0].toLowerCase()) {

            case "title": {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    TitleUtil.title(player, "", message, 10, 60, 10);
                }
                return;
            }

            case "chat": {
                Bukkit.broadcastMessage(TextUtil.color(message));

            }
        }
    }
}
