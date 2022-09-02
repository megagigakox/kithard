package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class AdminChatCommand {

    @FunnyCommand(
            name = "adminchat",
            playerOnly = true,
            aliases = {"ac"},
            acceptsExceeded = true,
            permission = "kithard.commands.adminchat"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/adminchat (wiadomosc)");
            return;
        }

        String message = StringUtils.join(args, " ");
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (!a.hasPermission("kithard.commands.adminchat")) {
                continue;
            }

            TextUtil.message(a, "&8[&4ADMINCHAT&8] &7" + player.getName() + " &8-> &c" + message);
        }
    }
}
