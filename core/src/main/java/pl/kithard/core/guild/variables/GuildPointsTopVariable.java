package pl.kithard.core.guild.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.ranking.GuildRankingService;

public class GuildPointsTopVariable extends Variable {
    private final CorePlugin plugin;
    private final int i;

    public GuildPointsTopVariable(String name, int i, CorePlugin plugin) {
        super(name);
        this.i = i;
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        GuildRankingService guildRankingService = this.plugin.getGuildRankingService();
        String s;
        if (i < 10) {
            s = "0" + this.i + ". &7";
        } else {
            s = this.i + ". &7";
        }

        if (guildRankingService.getGuildPointsRanking().size() >= this.i) {
            Guild guild = guildRankingService.getGuildPointsRanking().get(this.i - 1);
            return s + guild.getTag() + " &8[&b" + guild.getPoints() + "&8]";
        }
        return s;
    }
}