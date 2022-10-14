package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

@FunnyComponent
public class PlayerInfoCommand {

    private final CorePlugin plugin;

    public PlayerInfoCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "gracz",
            playerOnly = true,
            acceptsExceeded = true,
            completer = "online-players:500"
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {

        if (args.length < 1) {
            Guild guild = this.plugin.getGuildCache().findByPlayer(player);
            getInfoOfPlayer(player, corePlayer, guild);
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByPlayerName(args[0]);
        getInfoOfPlayer(player, targetCorePlayer, guild);
    }

    private void getInfoOfPlayer(Player player, CorePlayer corePlayer, Guild guild) {

        TextUtil.message(player, "     &8&l&m--[&b&l&m---&b&l " + corePlayer.getName() + " &b&l&m---&8&l&m]--");
        TextUtil.message(player, "&8» &7Gildia: &f" + (guild != null ? guild.getTag() : "Brak"));
        TextUtil.message(player, "&8» &7Ranking: &f" + this.plugin.getPlayerRankingService().getPlayerPointsRankingPlace(corePlayer, this.plugin.getPlayerRankingService().getPlayerPointsRanking()) + " miejsce");
        TextUtil.message(player, "&8» &7Punkty: &f" + corePlayer.getPoints());
        TextUtil.message(player, "&8» &7Zabojstwa: &f" + corePlayer.getKills());
        TextUtil.message(player, "&8» &7Asysty: &f" + corePlayer.getAssists());
        TextUtil.message(player, "&8» &7Smierci: &f" + corePlayer.getDeaths());
        TextUtil.message(player, "&8» &7KD-Ratio: &f" + corePlayer.getKd());
        TextUtil.message(player, "&8» &7Kill-streak: &f" + corePlayer.getKillStreak());
        TextUtil.message(player, "&8» &7Spedzony czas: &f" + TimeUtil.formatTimeMillis(corePlayer.getSpendTime()));
        TextUtil.message(player, "&8» &7Historia gildii: &f" + (corePlayer.getGuildHistory().isEmpty() ? "Brak" : String.join("&8,&f ", corePlayer.getGuildHistory())));

    }
}
