package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class WhoIsCommand {

    private final CorePlugin plugin;

    public WhoIsCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "whois",
            permission = "kithard.commands.whois",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender, String[] args) {

        if (args.length < 1) {

            TextUtil.correctUsage(sender, "/whois (gracz)");
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);

        TextUtil.message(sender, "&7Gracz: &f" + corePlayer.getName());
        TextUtil.message(sender, "&7Tryb gamemode: &f" + ((corePlayer.source() == null) ? "offline" : corePlayer.source().getGameMode().toString().toUpperCase()));
        TextUtil.message(sender, "&7Tryb latania: &f" + ((corePlayer.source() == null) ? "offline" : (corePlayer.source().getAllowFlight() ? "wlaczony" : "wylaczony")));
        TextUtil.message(sender, "");
        TextUtil.message(sender, "&7Json: &f" + this.plugin.getGson().toJson(corePlayer));

    }
}
