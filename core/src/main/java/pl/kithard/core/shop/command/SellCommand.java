package pl.kithard.core.shop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.shop.item.ShopSellItem;
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

        int i = 0;
        for (ShopSellItem sellItem : this.plugin.getShopItemCache().getSellItems()) {
            while (InventoryUtil.hasItem(player, sellItem.getItem().getType(), sellItem.getItem().getAmount())) {

                if (corePlayer.isDisabledSellItem(sellItem)) {
                    break;
                }

                i++;
                InventoryUtil.removeItem(player, sellItem.getItem().getType(), sellItem.getItem().getAmount());
                corePlayer.setMoney(corePlayer.getMoney() + sellItem.getPrice());
                corePlayer.setEarnedMoney(corePlayer.getEarnedMoney() + sellItem.getPrice());
            }
        }

        if (i == 0) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie posiadasz żadnych itemkow do sprzedania!");
        }
        else {
            TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie sprzedano &f" + i + " &7itemkow! &7Twoj nowy stan konta wynosi: &3" + corePlayer.getMoney());
            corePlayer.setNeedSave(true);
        }
    }

}
