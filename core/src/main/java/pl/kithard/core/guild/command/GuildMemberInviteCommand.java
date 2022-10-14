package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildMemberInviteCommand {

    private final CorePlugin plugin;

    public GuildMemberInviteCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zapros",
            aliases = "g invite",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, Guild guild, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g zapros (gracz)");
            return;
        }

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.MEMBER_INVITE)) {
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        Player targetPlayer = targetCorePlayer.source();
        if (targetPlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        Guild targetGuild = this.plugin.getGuildCache().findByPlayer(targetPlayer);
        if (targetGuild != null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz ma już gildie");
            return;
        }

        if (guild.getMemberInvites().contains(targetPlayer.getUniqueId())) {
            guild.getMemberInvites().remove(targetPlayer.getUniqueId());
            TextUtil.message(targetPlayer, "&8(&3&l!&8) &7Zaproszenie do gildii &8[&b" + guild.getTag() + "&7] &7zostalo cofniete przez &b" + player.getName() + "&7.");
            TextUtil.message(player, "&8(&3&l!&8) &7Cofnales zaproszenie do gildii dla gracza &b" + targetPlayer.getName() + "&7.");
            return;
        }

        GuildLog guildLog = new GuildLog(
                guild.getTag(),
                GuildLogType.MEMBER_INVITE,
                "&f" + player.getName() + " &7zaprosil gracza &f" + targetPlayer.getName() + " &7do gildii."
        );
        guild.addLog(guildLog);
        this.plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(this.plugin, () -> this.plugin.getGuildRepository().insertLog(guildLog));

        guild.getMemberInvites().add(targetPlayer.getUniqueId());
        TextUtil.message(targetPlayer, " &7Zostales zaproszony do gildii &8[&b" + guild.getTag() + "&8] &7przez &b" + player.getName() + "&7.");
        TextUtil.message(targetPlayer, " &7Wpisz &b/g dolacz " + guild.getTag() + " &7aby dolaczyc do gildii.");
        TextUtil.message(player, "&8(&3&l!&8) &7Wyslales zaproszenie do gildii dla gracza &b" + targetPlayer.getName() + "&7.");
    }

}
