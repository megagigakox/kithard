package pl.kithard.core.guild.ranking;

import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

import java.util.ArrayList;
import java.util.List;

public class GuildRankingService {

    private final CorePlugin plugin;

    private final List<Guild> guildPointsRanking = new ArrayList<>();

    public GuildRankingService(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void sort() {
        this.guildPointsRanking.removeIf(guild -> this.plugin.getGuildCache().findByTag(guild.getTag()) == null);

        for (Guild guild : this.plugin.getGuildCache().getValues()) {
            if (guild.getGuildMemebrs().size() < 3) {
                this.guildPointsRanking.remove(guild);
            }
            else if (guild.getGuildMemebrs().size() >= 3 && !this.guildPointsRanking.contains(guild))  {
                this.guildPointsRanking.add(guild);
            }
        }

        this.guildPointsRanking.sort(((o1, o2) -> Integer.compare(o2.getPoints(), o1.getPoints())));
    }

    public List<Guild> getGuildPointsRanking() {
        return guildPointsRanking;
    }
}
