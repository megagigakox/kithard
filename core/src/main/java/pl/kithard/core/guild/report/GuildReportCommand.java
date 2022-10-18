package pl.kithard.core.guild.report;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildReportCommand {

    private final CorePlugin plugin;

    public GuildReportCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zglos",
            aliases = "g report",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(player, "/g zglos (tag) (powod)");
            return;
        }

        String tag = args[0];
        Guild guild = this.plugin.getGuildCache().findByTag(tag);
        if (guild == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cGildia o takim tagu nie istnieje!");
            return;
        }


        if (this.plugin.getGuildReportCache().find(tag.toUpperCase()) != null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTa gildia zostala juz zgloszona!");
            return;
        }

        String message = StringUtils.join(args, " ", 1, args.length).replace("&", "");
        GuildReport guildReport = new GuildReport(tag.toUpperCase(), player.getName(), message);
        this.plugin.getGuildReportCache().add(guildReport);
        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie wyslano zgloszenie");
    }
}
