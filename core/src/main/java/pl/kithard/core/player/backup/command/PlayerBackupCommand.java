package pl.kithard.core.player.backup.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.backup.gui.PlayerBackupGui;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class PlayerBackupCommand {

    private final CorePlugin plugin;

    public PlayerBackupCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "backup",
            permission = "kithard.commands.backup",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.message(player, "/backup (gracz)");
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (corePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        new PlayerBackupGui(plugin).open(player, corePlayer);
    }

}
