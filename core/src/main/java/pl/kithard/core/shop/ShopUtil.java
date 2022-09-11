package pl.kithard.core.shop;

import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.shop.item.ShopItem;
import pl.kithard.core.shop.item.ShopItemType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

public class ShopUtil {

    public static void sellAll(Player player, CorePlayer corePlayer, CorePlugin plugin) {

        int i = 0;
        for (ShopItem sellItem : plugin.getShopConfiguration().getItems()) {

            if (sellItem.getType() != ShopItemType.SELL) {
                continue;
            }

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
        } else {
            TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie sprzedano &f" + i + " &7itemkow! &7Twoj nowy stan konta wynosi: &3" + corePlayer.getMoney());
            corePlayer.setNeedSave(true);
        }
    }

}
