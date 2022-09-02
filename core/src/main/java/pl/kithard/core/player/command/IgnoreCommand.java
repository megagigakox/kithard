package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class IgnoreCommand {

    private final CorePlugin plugin;

    public IgnoreCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "ignore",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/ignore (all|gracz)");
            return;
        }

        if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("*")) {

            if (corePlayer.isDisabledSetting(PlayerSettings.PRIVATE_MESSAGES)) {
                corePlayer.removeDisableSetting(PlayerSettings.PRIVATE_MESSAGES);
                corePlayer.getIgnoredPlayers().clear();
                corePlayer.setNeedSave(true);
                TextUtil.message(player, "&8[&3&l!&8] &7Od tej pory &bnie ignorujesz &7nikogo!");
                return;
            }

            corePlayer.addDisabledSetting(PlayerSettings.PRIVATE_MESSAGES);
            corePlayer.setNeedSave(true);
            TextUtil.message(player, "&8[&3&l!&8] &7Od tej pory &bignorujesz &7każdego!");
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

        if (corePlayer.isIgnoredUuid(targetCorePlayer.getUuid())) {
            corePlayer.removeIgnoredUuid(targetCorePlayer.getUuid());
            corePlayer.setNeedSave(true);
            TextUtil.message(player, "&8[&3&l!&8] &7Usunieto gracza &b" + targetCorePlayer.getName() + " &7z listy ignorowanych graczy!");
            return;
        }

        corePlayer.addIgnoredUuid(targetCorePlayer.getUuid());
        corePlayer.setNeedSave(true);
        TextUtil.message(player, "&8[&3&l!&8] &7Dodano gracza &b" + targetCorePlayer.getName() + " &7do listy ignorowanych graczy!");
    }
}
