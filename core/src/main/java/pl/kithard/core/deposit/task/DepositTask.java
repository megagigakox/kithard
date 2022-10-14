package pl.kithard.core.deposit.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.deposit.DepositItem;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

public class DepositTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public DepositTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 60L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == null) {
                continue;
            }

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (corePlayer == null) {
                continue;
            }

            for (DepositItem depositItem : this.plugin.getDepositItemConfiguration().getDepositItems()) {
                int itemToRemove = InventoryUtil.countItemsIgnoreItemMeta(player, depositItem.getItem());

                if (itemToRemove > depositItem.getLimit()) {
                    int amountToRemove = itemToRemove - depositItem.getLimit();
                    ItemStack itemStack = depositItem.getItem().clone();
                    itemStack.setAmount(amountToRemove);
                    InventoryUtil.removeItemIgnoreItemMeta(player, itemStack);
                    corePlayer.addToDeposit(depositItem, amountToRemove);
                    TextUtil.message(player, depositItem.getMessage()
                            .replace("{AMOUNT}", String.valueOf(amountToRemove))
                            .replace("{OWNED-AMOUNT}", String.valueOf(corePlayer.getAmountOfDepositItem(depositItem.getName()))));
                }
            }
        }
    }
}
