package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildJoinCommand {

    private final CorePlugin plugin;

    public GuildJoinCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g dolacz",
            aliases = "g join",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g dolacz (tag)");
            return;
        }

        Guild playerGuild = this.plugin.getGuildCache().findByPlayer(player);
        if (playerGuild != null) {
            TextUtil.message(player, "&8[&4&l!&8] &cPosiadasz już gildie!");
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cPodana gildia &4nie istnieje&c!");
            return;
        }

        if (!guild.getMemberInvites().contains(player.getUniqueId())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie masz &4zaproszenia &cdo tej gildii!");
            return;
        }

        GuildMember guildMember = new GuildMember(player.getUniqueId(), player.getName());
        guild.getMemberInvites().remove(player.getUniqueId());
        guild.getMembers().add(guildMember);

        guild.addLog(new GuildLog(
                GuildLogType.MEMBER_JOIN,
                "&f" + player.getName() + " &7dolaczyl do gildii.")
        );

        corePlayer.getGuildHistory().add(guild.getTag());

        guild.setNeedSave(true);
        Bukkit.broadcastMessage(TextUtil.color("&8[&3&l!&8] &7Gracz &f" + player.getName() + " &7dolaczyl do gildii &8[&b" + guild.getTag() + "&8]&7!"));

    }

}
