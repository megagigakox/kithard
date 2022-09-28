package pl.kithard.core.itemshop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.itemshop.ItemShopService;
import pl.kithard.core.itemshop.ItemShopServiceConfiguration;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class ItemShopCommand {

    private final CorePlugin plugin;

    public ItemShopCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "is",
            acceptsExceeded = true,
            permission = "kithard.commands.itemshop",
            completer = "online-players:5 itemShopServices:20"
    )
    public void handle(CommandSender sender, String[] args) {
        ItemShopServiceConfiguration itemShopServiceConfiguration = this.plugin.getItemShopServiceConfiguration();

        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/is (gracz) (usluga)");
            TextUtil.message(sender, "&7Dostepne uslugi:");
            for (ItemShopService service : itemShopServiceConfiguration.getServices()) {
                TextUtil.message(sender, " &8- &b" + service.getName());
            }
            return;
        }

        ItemShopService itemShopService = itemShopServiceConfiguration.findByKey(args[1]);
        if (itemShopService == null) {
            TextUtil.message(sender, "&8(&4&l!&8) &cUsluga o nazwie &4" + args[1] + " &cnie istnieje!");
            TextUtil.message(sender, "&7Dostepne uslugi:");
            for (ItemShopService service : this.plugin.getItemShopServiceConfiguration().getServices()) {
                TextUtil.message(sender, " &8- &b" + service.getName());
            }
            return;
        }

        this.plugin.getItemShopServiceExecutor().execute(args[0], itemShopService);
        TextUtil.message(sender, "&8(&3&l!&8) &7Pomyslnie nadano usluge &b" + itemShopService.getName() + " &7dla gracza &f" + args[0]);
    }
}
