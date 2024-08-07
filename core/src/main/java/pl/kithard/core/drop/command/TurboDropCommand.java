package pl.kithard.core.drop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
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
            this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
                    this.plugin.getServerSettingsConfiguration().save());

            Bukkit.getOnlinePlayers().forEach(it -> TitleUtil.title(it, "", "&b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &7zostal aktywowany na: &3" +
                    TimeUtil.formatTimeMillis(time - System.currentTimeMillis()), 20, 40, 20));

            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (corePlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        long time2 = TimeUtil.timeFromString(args[1]);
        if (corePlayer.getTurboDrop() > System.currentTimeMillis()) {
            corePlayer.setTurboDrop(corePlayer.getTurboDrop() + time2);
        }
        else {
            corePlayer.setTurboDrop(time2 + System.currentTimeMillis());
        }

        if (corePlayer.source() != null) {
            TextUtil.message(corePlayer.source(), "&8(&3&l!&8) &7Aktywowano &b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &7dla ciebie na: &f" +
                    TimeUtil.formatTimeMillis(corePlayer.getTurboDrop() - System.currentTimeMillis()));
        }

        corePlayer.setNeedSave(true);
        TextUtil.message(player, "&aPomyslnie nadano turbo");
    }

    @FunnyCommand(
            name = "gturbo",
            permission = "kithard.commands.turbo",
            acceptsExceeded = true
    )
    public void handleGuild(CommandSender player, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(player, "/gturbo (tag) (time)");
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(player, "guild null");
            return;
        }

        long time = TimeUtil.timeFromString(args[1]);
        if (guild.getTurboDrop() > System.currentTimeMillis()) {
            guild.setTurboDrop(guild.getTurboDrop() + time);
        }
        else {
            guild.setTurboDrop(time + System.currentTimeMillis());
        }

        guild.setNeedSave(true);
        TextUtil.message(player, "&aPomyslnie nadano turbo");
    }


}
