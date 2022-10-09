package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.ranking.PlayerRankingService;

public class PlayerPointsTopVariable extends Variable {
    private final CorePlugin plugin;
    private final int i;

    public PlayerPointsTopVariable(String name, int i, CorePlugin plugin) {
        super(name);

        this.i = i;
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        PlayerRankingService playerRankingService = this.plugin.getPlayerRankingService();

        String s;
        if (i < 10) {
            s = "0" + this.i + ". &7";
        } else {
            s = this.i + ". &7";
        }

        if (playerRankingService.getPlayerPointsRanking().size() >= this.i) {
            CorePlayer corePlayer = playerRankingService.getPlayerPointsRanking().get(this.i - 1);
            return s + corePlayer.getName() + " &8[&3" + corePlayer.getPoints() + "&8]";
        }

        return s;
    }
}
