package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildKickMemberCommand {

    private final CorePlugin plugin;

    public GuildKickMemberCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g wyrzuc",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, Guild guild, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g wyrzuc (gracz)");
            return;
        }

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.MEMBER_KICK)) {
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        if (!guild.isMember(targetCorePlayer.getUuid())) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie jest &cw twojej gildii!");
            return;
        }

        if (guild.isOwner(targetCorePlayer.getUuid())) {
            TextUtil.message(player, "&8[&4&l&8] &cNie mozesz &4wyrzucic &czalozyciela");
            return;
        }

        if (player.getUniqueId().equals(targetCorePlayer.getUuid())) {
            TextUtil.message(player, "&8[&4&l&8] &cNie mozesz &4wyrzucic samego siebie!");
            return;
        }

        GuildMember guildMember = guild.findMemberByUuid(targetCorePlayer.getUuid());
        if (guild.isDeputy(targetCorePlayer.getUuid())) {
            guild.getDeputies().remove(guildMember);
        }

        guild.addLog(new GuildLog(
                GuildLogType.MEMBER_KICK,
                "&f" + player.getName() + " &7wyrzucil gracza &f" + guildMember.getName() + " &7z gildii.")
        );
        guild.getMembers().remove(guildMember);
        guild.setNeedSave(true);
        Bukkit.broadcastMessage(TextUtil.color(
                "&8[&3&l!&8] &7Gracz &f" +
                        targetCorePlayer.getName() +
                        " &7zostal wyrzucony z gildii &8[&b" +
                        guild.getTag() +
                        "&8] &7przez &f" +
                        player.getName() +
                        "&7!"));
    }
}
