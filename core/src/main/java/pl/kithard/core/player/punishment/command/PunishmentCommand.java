package pl.kithard.core.player.punishment.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.punishment.type.Mute;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.punishment.type.Ban;
import pl.kithard.core.player.punishment.type.BanIP;

@FunnyComponent
public class PunishmentCommand {

    private final CorePlugin plugin;

    public PunishmentCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "ban",
            permission = "kithard.commands.ban",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void ban(CommandSender sender, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/ban (gracz) (powod)");
            return;
        }

        Ban ban = this.plugin.getPunishmentCache().findBan(args[0]);
        if (ban != null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz jest już zbanowany!");
            return;
        }

        String reason = StringUtils.join(args, " ", 1, args.length);

        this.plugin.getPunishmentFactory()
                .assignBanToPlayer(
                        args[0],
                        sender.getName(),
                        0L,
                        reason);

        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cGracz &4"
                                + args[0]
                                + " &czostal permanentnie zbanowany przez &4"
                                + sender.getName() +
                                " &cz powodem: &4"
                                + reason + "&c."));
    }

    @FunnyCommand(
            name = "tempban",
            permission = "kithard.commands.tempban",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void tempBan(CommandSender sender, String[] args) {
        if (args.length < 3) {
            TextUtil.correctUsage(sender, "/tempban (gracz) (czas) (powod)");
            return;
        }

        Ban ban = this.plugin.getPunishmentCache().findBan(args[0]);
        if (ban != null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz jest już zbanowany!");
            return;
        }

        String reason = StringUtils.join(args, " ", 2, args.length);

        this.plugin.getPunishmentFactory()
                .assignBanToPlayer(
                        args[0],
                        sender.getName(),
                        TimeUtil.parseDateDiff(args[1], true),
                        reason);

        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cGracz &4"
                                + args[0]
                                + " &czostal zbanowany tymczasowo przez &4"
                                + sender.getName()
                                + " &cz powodem: &4"
                                + reason + "&c."));
    }

    @FunnyCommand(
            name = "banip",
            permission = "kithard.commands.banip",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void banIP(CommandSender sender, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/banip (gracz/ip) (powod)");
            return;
        }

        BanIP ban = this.plugin.getPunishmentCache().findBanIP(args[0]);
        if (ban != null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz jest już zbanowany!");
            return;
        }

        String reason = StringUtils.join(args, " ", 1, args.length);
        CorePlayer punishedPlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (punishedPlayer != null) {
            Bukkit.broadcastMessage(
                    TextUtil.color(
                            "&8[&4&l!&8] &cGracz &4"
                                    + punishedPlayer.getName()
                                    + " &czostal zbanowany na ip przez &4"
                                    + sender.getName()
                                    + " &cz powodem: &4"
                                    + reason + "&c."));

            this.plugin.getPunishmentFactory()
                    .assignBanIPToPlayer(
                            punishedPlayer.getIp(),
                            punishedPlayer.getName(),
                            sender.getName(),
                            reason);
            return;
        }

        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cIP &4"
                                + args[0]
                                .replace("1", "*")
                                .replace("2", "*")
                                .replace("3", "*")
                                .replace("5", "*")
                                + " &czostalo zbanowane przez &4"
                                + sender.getName()
                                + " &cz powodem: &4"
                                + reason + "&c."));

        this.plugin.getPunishmentFactory()
                .assignBanIPToPlayer(
                        args[0],
                        args[0],
                        sender.getName(),
                        reason);
    }

    @FunnyCommand(
            name = "unban",
            permission = "kithard.commands.unban",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void unban(CommandSender sender, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(sender, "/unban (gracz)");
            return;
        }

        Ban ban = this.plugin.getPunishmentCache().findBan(args[0]);
        if (ban == null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz &4nie jest &czbanowany!");
            return;
        }

        this.plugin.getPunishmentFactory().reassignBanFromPlayer(args[0]);
        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cGracz &4"
                                + args[0]
                                + " &czostal odbanowany przez &4"
                                + sender.getName() + "&c!"));
    }

    @FunnyCommand(
            name = "unbanip",
            permission = "kithard.commands.unbanip",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void unbanIP(CommandSender sender, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(sender, "/unbanip (ip)");
            return;
        }

        BanIP ban = this.plugin.getPunishmentCache().findBanIP(args[0]);
        if (ban == null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTo IP &4nie jest &czbanowane!");
            return;
        }

        String hideIp = args[0]
                .replace("1", "*")
                .replace("2", "*")
                .replace("3", "*")
                .replace("5", "*");

        this.plugin.getPunishmentFactory().reassignBanIPFromPlayer(args[0]);
        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cIP &4"
                                + hideIp
                                + " &czostalo odbanowane przez &4"
                                + sender.getName() + "&c!"));

    }

    @FunnyCommand(
            name = "mute",
            permission = "kithard.commands.mute",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void mute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/mute (gracz) (powod)");
            return;
        }

        Mute mute = this.plugin.getPunishmentCache().findMute(args[0]);
        if (mute != null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz &4jest już &cwyciszony!");
            return;
        }

        String reason = StringUtils.join(args, " ", 1, args.length);
        this.plugin.getPunishmentFactory().assignMuteToPlayer(args[0], sender.getName(), 0L, reason);

        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cGracz &4"
                                +
                                args[0]
                                + " &czostal permanentnie wyciszony przez &4"
                                + sender.getName()
                                + " &cz powodem: &4"
                                + reason + "&c."));
    }

    @FunnyCommand(
            name = "tempmute",
            permission = "kithard.commands.tempmute",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void tempMute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            TextUtil.correctUsage(sender, "/mute (gracz) (czas) (powod)");
            return;
        }

        Mute mute = this.plugin.getPunishmentCache().findMute(args[0]);
        if (mute != null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz &4jest już &cwyciszony!");
            return;
        }

        String reason = StringUtils.join(args, " ", 2, args.length);
        this.plugin.getPunishmentFactory()
                .assignMuteToPlayer(args[0], sender.getName(), TimeUtil.parseDateDiff(args[1], true), reason);

        Bukkit.broadcastMessage(
                TextUtil.color(
                                "&8[&4&l!&8] &cGracz &4"
                                        +
                                        args[0]
                                        + " &czostal tymczasowo wyciszony przez &4"
                                        + sender.getName()
                                        + " &cz powodem: &4"
                                        + reason + "&c."));
    }


    @FunnyCommand(
            name = "unmute",
            permission = "kithard.commands.unmute",
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void unMute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(sender, "/unban (gracz)");
            return;
        }

        Mute mute = this.plugin.getPunishmentCache().findMute(args[0]);
        if (mute == null) {
            TextUtil.message(sender, "&8[&4&l!&8] &cTen gracz &4nie jest &cwyciszony!");
            return;
        }

        this.plugin.getPunishmentFactory().reassignMuteFromPlayer(args[0]);
        Bukkit.broadcastMessage(
                TextUtil.color(
                        "&8[&4&l!&8] &cGracz &4"
                                + args[0]
                                + " &czostal odciszony przez &4"
                                + sender.getName() + "&c!"));


    }


}
