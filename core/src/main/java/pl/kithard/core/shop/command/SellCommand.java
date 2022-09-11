package pl.kithard.core.shop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.shop.ShopUtil;
import pl.kithard.core.shop.item.ShopItem;
import pl.kithard.core.shop.item.ShopItemType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class SellCommand {

    private final CorePlugin plugin;

    public SellCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "sellall",
            aliases = "sell",
            acceptsExceeded = true,
            playerOnly = true,
            async = true
    )
    public void handle(Player player, CorePlayer corePlayer) {
        ShopUtil.sellAll(player, corePlayer, plugin);
    }

}
