package pl.kithard.core.itemshop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.TextUtil;

public class ItemShopServiceExecutor {

    private final CorePlugin plugin;

    public ItemShopServiceExecutor(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void execute(String playerName, ItemShopService service) {
        for (String s : service.getCommands()) {
            this.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("{PLAYER}", playerName));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

            if (!corePlayer.isDisabledSetting(PlayerSettings.ITEM_SHOP_SERVICES_MESSAGES)) {
                for (String s : service.getMessages()) {
                    TextUtil.message(player, s.replace("{PLAYER}", playerName));
                }
            }
        }

        if (!service.getItems().isEmpty()) {
            Player player = this.plugin.getServer().getPlayerExact(playerName);
            if (player == null) {
                return;
            }

            for (ItemStack is : service.getItems()) {
                player.getInventory().addItem(is);
            }
        }
    }
}
