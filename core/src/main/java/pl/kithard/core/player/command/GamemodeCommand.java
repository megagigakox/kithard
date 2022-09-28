package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.NumberUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GamemodeCommand {

    private final CorePlugin plugin;

    private final GameMode[] gameModes = {
            GameMode.SURVIVAL,
            GameMode.CREATIVE,
            GameMode.ADVENTURE,
            GameMode.SPECTATOR
    };

    public GamemodeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "gamemode",
            aliases = "gm",
            permission = "kithard.commands.gamemode",
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/gamemode (0/1/2/3) (player)");
            return;
        }

        GameMode gameMode = findGameModeFromString(args[0]);
        if (gameMode == null) {
            TextUtil.correctUsage(player, "/gamemode (0/1/2/3) (player)");
            return;
        }

        switch (args.length) {
            case 1: {
                player.setGameMode(gameMode);
                TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3ustawiono &7tryb gry na: &b" +
                        gameMode.name().toUpperCase() + "&7!");
                return;
            }
            case 2: {
                Player target = this.plugin.getServer().getPlayerExact(args[1]);
                if (target == null) {
                    TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
                    return;
                }

                target.setGameMode(gameMode);
                TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &3ustawiono &7tryb gry na: &b" +
                        gameMode.name().toUpperCase() + " &7dla gracza &f" + target.getName() + "&7!");
            }
        }
    }

    private GameMode findGameModeFromString(String args) {
        if (NumberUtil.isInteger(args)) {
            try {
                return this.gameModes[Integer.parseInt(args)];
            } catch (IndexOutOfBoundsException ignored) {
                return null;
            }

        } else {

            for (GameMode mode : this.gameModes) {
                if (mode.name().equalsIgnoreCase(args.toUpperCase())) {
                    return mode;
                }
            }

            return null;
        }
    }
}
