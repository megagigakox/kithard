package pl.kithard.core.drop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.util.TitleUtil;

@FunnyComponent
public class TurboDropCommand {

    private final CorePlugin plugin;

    public TurboDropCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "turbo",
            permission = "kithard.commands.turbo",
            acceptsExceeded = true
    )
    public void handle(CommandSender player, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(player, "/turbo (gracz|all) (time)");
            return;
        }

        long time = TimeUtil.timeFromString(args[1]) + System.currentTimeMillis();
        if (args[0].equalsIgnoreCase("all")) {
            ServerSettings serverSettings = this.plugin.getServerSettings();

            serverSettings.setTurboDrop(time);
            this.plugin.getExecutorService().execute(() -> this.plugin.getServerSettingsService().save(serverSettings));

            Bukkit.getOnlinePlayers().forEach(it -> TitleUtil.title(it, "", "&b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &7zostal aktywowany na: &3" +
                    TimeUtil.formatTimeMillis(time - System.currentTimeMillis()), 20, 40, 20));

            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (corePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        long time2 = TimeUtil.timeFromString(args[1]) + System.currentTimeMillis();
        corePlayer.setTurboDrop((corePlayer.getTurboDrop() - System.currentTimeMillis()) + time2);
        if (corePlayer.source() != null) {
            TextUtil.message(corePlayer.source(), "&8[&3&l!&8] &7Aktywowano &b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &7dla ciebie na: &f" +
                    TimeUtil.formatTimeMillis(corePlayer.getTurboDrop() - System.currentTimeMillis()));
        }

        TextUtil.message(player, "&aPomyslnie nadano turbo");
    }

}
