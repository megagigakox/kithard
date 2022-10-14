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
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildMemberLeaveCommand {

    private final CorePlugin plugin;

    public GuildMemberLeaveCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g opusc",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (guild.isOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie możesz &4opuscic gildii &cbedac jej zalozycielem!");
            return;
        }

        GuildMember member = guild.findMemberByUuid(player.getUniqueId());
        if (guild.isDeputy(player.getUniqueId())) {
            guild.getDeputies().remove(player.getUniqueId());
        }

        GuildLog guildLog = new GuildLog(
                guild.getTag(),
                GuildLogType.MEMBER_QUIT,
                "&f" + player.getName() + " &7opuscil gildie."
        );
        guild.addLog(guildLog);
        this.plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(this.plugin, () -> {
                    this.plugin.getGuildRepository().insertLog(guildLog);
                    this.plugin.getGuildRepository().deleteMember(member);
                });

        guild.getMembers().remove(member);
        guild.setNeedSave(true);

        Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Gracz &f" + player.getName() + " &7opuscil gildie &8[&b" + guild.getTag() + "&8]&7!"));
    }

}