package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TitleUtil;

@FunnyComponent
public class GuildAlertCommand {

    private final CorePlugin plugin;

    public GuildAlertCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g alert",
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g alert (wiadomosc)");
            return;
        }

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_ALERT_ACCESS)) {
            return;
        }

        String message = ChatColor.stripColor(StringUtils.join(args, " ", 0, args.length));
        for (GuildMember guildMember : guild.getGuildMemebrs()) {
            Player other = Bukkit.getPlayer(guildMember.getUuid());
            if (other == null) {
                continue;
            }

            TitleUtil.title(other, "&3&lAlert gildyjny", message, 20, 80, 20);
        }
    }
}
