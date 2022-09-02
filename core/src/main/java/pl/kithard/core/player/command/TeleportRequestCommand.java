package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class TeleportRequestCommand {

    private final CorePlugin plugin;

    public TeleportRequestCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "tpa",
            aliases = {"tprequest", "tparequest", "teleport"},
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/tpa (gracz)");
            return;
        }

        if (args[0].equalsIgnoreCase(player.getName())) {
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }
        Player targetPlayer = targetCorePlayer.source();
        if (targetPlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        targetCorePlayer.removeTeleportRequest(player.getUniqueId());
        targetCorePlayer.addTeleportRequest(player.getUniqueId());
        TextUtil.message(player, "&8[&3&l!&8] &7Wyslano prosbe o &3teleportacje &7do gracza &b" + targetPlayer.getName());
        TextUtil.message(targetPlayer, "&8[&3&l!&8] &7Gracz &b" + player.getName() + " &7wyslal prosbe o teleportacje!");
        TextUtil.message(targetPlayer, "    &7Wpisz &f/tpaccept " + player.getName() + " &7aby zaakceptowac.");
        TextUtil.message(targetPlayer, "    &7Wpisz &f/tpdeny " + player.getName() + " &7aby odrzucic.");
    }

}
