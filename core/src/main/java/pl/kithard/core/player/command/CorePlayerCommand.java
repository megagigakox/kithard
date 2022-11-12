package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.NumberUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class CorePlayerCommand {

    private final CorePlugin plugin;

    public CorePlayerCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "cpm",
            permission = "kithard.commands.cpm",
            acceptsExceeded = true
    )
    public void handle(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            TextUtil.correctUsage(commandSender, "/cpm (player) (points/kills/deaths/assists) (value)");
            return;
        }


        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (corePlayer == null) {
            return;
        }

        if (!NumberUtil.isInteger(args[2])) {
            TextUtil.message(commandSender, "&8(&4&l!&8) &cTylko liczby!");
            return;
        }
        int value = Integer.parseInt(args[2]);

        switch (args[1]) {

            case "points": {

                corePlayer.setPoints(value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc punktow wynosi: &2" + corePlayer.getPoints());
                return;
            }

            case "kills": {
                corePlayer.setKills(value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc killi wynosi: &2" + corePlayer.getKills());
                return;
            }

            case "deaths": {
                corePlayer.setDeaths(value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc pieniedzy wynosi: &2" + corePlayer.getDeaths());
                return;
            }

            case "assists": {
                corePlayer.setAssists(value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc asyst wynosi: &2" + corePlayer.getAssists());
            }

        }

    }

    @FunnyCommand(
            name = "trytofixtime",
            permission = "kithard.commands.ttft",
            acceptsExceeded = true
    )
    public void hTtft(CommandSender commandSender, String[] args) {
        int i = 0;
        for (CorePlayer corePlayer : this.plugin.getCorePlayerCache().getValues()) {
            if (corePlayer.getSpendTime() - System.currentTimeMillis() > System.currentTimeMillis()) {
                commandSender.sendMessage("poprawiono" + i++);
                corePlayer.setSpendTime(corePlayer.getSpendTime() - System.currentTimeMillis());
            }
        }
    }
}
