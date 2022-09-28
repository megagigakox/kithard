package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class TpCommand {

    private final CorePlugin plugin;

    public TpCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "tp",
            permission = "kithard.commands.tp",
            acceptsExceeded = true,
            completer = "online-players:100"
    )
    public void handle(Player player, String[] args) {

        switch (args.length) {

            case 1: {

                Player targetPlayer = this.plugin.getServer().getPlayerExact(args[0]);
                if (targetPlayer == null) {
                    TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
                    return;
                }

                player.teleport(targetPlayer);
                TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3przeteleportowano &7do gracza &b" + targetPlayer.getName() + "&7!");
                return;
            }

            case 2: {

                Player targetPlayer1 = this.plugin.getServer().getPlayerExact(args[0]);
                Player targetPlayer2 = this.plugin.getServer().getPlayerExact(args[1]);
                if (targetPlayer1 == null || targetPlayer2 == null) {
                    TextUtil.message(player, "&8(&4&l!&8) &cTen gracz &4nie istnieje &cw bazie danych!");
                    return;
                }

                targetPlayer1.teleport(targetPlayer2);
                TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3przeteleportowano &7gracza &b" + targetPlayer1.getName() + " &7do &b" + targetPlayer2.getName());
                return;
            }

            case 3: {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);

                player.teleport(new Location(player.getWorld(), x, y, z));
                TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3przeteleportowano &7na kordynaty: &bx:" + x + ", y:" + y + ", z:" + z);
                return;
            }

            default: TextUtil.correctUsage(player, "/tp (gracz) &7lub &b(gracz) -> (gracz2) &7lub &b(x) (y) (z)");

        }


    }

}