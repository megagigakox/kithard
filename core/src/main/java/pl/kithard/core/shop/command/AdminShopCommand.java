package pl.kithard.core.shop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.NumberUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class AdminShopCommand {

    private final CorePlugin plugin;

    public AdminShopCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "adminshop",
            permission = "kithard.commands.adminshop",
            acceptsExceeded = true
    )
    public void handle(CommandSender commandSender, String[] args) {

        if (args.length < 3) {
            TextUtil.correctUsage(commandSender, "/adminshop (player) (set/add/remove) (value)");
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

            case "set": {

                corePlayer.setMoney(value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc pieniedzy wynosi: &2" + corePlayer.getMoney());
                return;
            }

            case "add": {
                corePlayer.setMoney(corePlayer.getMoney() + value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc pieniedzy wynosi: &2" + corePlayer.getMoney());
                return;
            }

            case "remove": {
                corePlayer.setMoney(corePlayer.getMoney() - value);
                TextUtil.message(commandSender, "&8(&2&l!&8) &aNowa wartosc pieniedzy wynosi: &2" + corePlayer.getMoney());
            }

        }

    }
}
