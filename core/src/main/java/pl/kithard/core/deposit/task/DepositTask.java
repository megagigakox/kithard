package pl.kithard.core.deposit.task;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        this.runTaskTimer(plugin, 60L, 60L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player == null) {
                continue;
            }

            if (LocationUtil.isInSpawn(player.getLocation())) {
                continue;
            }

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

            for (DepositItem depositItem : this.plugin.getDepositItemCache().getDepositItems()) {
                int itemToRemove = InventoryUtil.countAmountForDeposit(player, depositItem.getItem());

                if (itemToRemove > depositItem.getLimit()) {
                    int amountToRemove = itemToRemove - depositItem.getLimit();

                    ItemStack itemStack = depositItem.getItem().clone();
                    itemStack.setAmount(amountToRemove);

                    if (itemStack.getType() != Material.TNT) {
                        InventoryUtil.remove(itemStack,player, amountToRemove);
                    } else {
                        InventoryUtil.removeItemByDisplayName(player, itemStack);
                    }

                    corePlayer.addToDeposit(depositItem, amountToRemove);
                    TextUtil.message(player, depositItem.getMessage()
                            .replace("{AMOUNT}", String.valueOf(amountToRemove))
                            .replace("{OWNED-AMOUNT}", String.valueOf(corePlayer.getAmountOfDepositItem(depositItem.getId()))));
                }
            }
        }
    }
}
