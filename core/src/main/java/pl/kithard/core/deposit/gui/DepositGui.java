package pl.kithard.core.deposit.gui;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.deposit.DepositItem;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.*;

public class DepositGui {

    private final CorePlugin plugin;

    public DepositGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, CorePlayer corePlayer) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Schowek:"))
                .rows(5)
                .create();

        GuiHelper.fillColorGui5(gui);

        for (DepositItem depositItem : this.plugin.getDepositItemCache().getDepositItems()) {
            gui.setItem(depositItem.getSlot(), ItemStackBuilder.of(depositItem.getItem().clone())
                    .amount(depositItem.getLimit())
                    .name("&b&l" + depositItem.getName())
                    .lore(
                            "",
                            " &8» &7W schowku posiadasz: &f" + corePlayer.getAmountOfDepositItem(depositItem.getId()),
                            " &8» &7Aktualny limit w eq: &f" + depositItem.getLimit(),
                            "",
                            " &7Kliknij &flewym &7aby wyplacic limit!",
                            " &7Kliknij &fprawym &7aby wplacic limit!"
                    )
                    .asGuiItem(event -> {

                        if (event.getClick() == ClickType.LEFT) {
                            int amountInInventory = InventoryUtil.countAmountForDeposit(player, depositItem.getItem());

                            if (amountInInventory >= depositItem.getLimit())
                                return;

                            if (corePlayer.getAmountOfDepositItem(depositItem.getId()) <= 0) return;

                            int i = depositItem.getLimit() - amountInInventory;
                            if (LocationUtil.isInSpawn(player.getLocation()))
                                i = depositItem.getItem().getMaxStackSize();

                            if (i > corePlayer.getAmountOfDepositItem(depositItem.getId()))
                                i = corePlayer.getAmountOfDepositItem(depositItem.getId());

                            corePlayer.removeFromDeposit(depositItem, i);
                            open(player, corePlayer);

                            ItemStack toAdd = depositItem.getItem().clone();
                            toAdd.setAmount(i);

                            InventoryUtil.addItem(player, toAdd);

                        } else if (event.getClick() == ClickType.RIGHT) {
                            int amountInInventory = InventoryUtil.countAmountForDeposit(player, depositItem.getItem());
                            if (amountInInventory != 0) {
                                corePlayer.addToDeposit(depositItem, amountInInventory);
                                open(player, corePlayer);

                                ItemStack toRemove = depositItem.getItem().clone();
                                toRemove.setAmount(amountInInventory);

                                if (toRemove.getType() != Material.TNT) {

                                    InventoryUtil.removeItemIgnoreItemMeta(player, toRemove);

                                } else {

                                    InventoryUtil.removeItemByDisplayName(player, toRemove);

                                }
                            }
                        }

                    }));
        }

        gui.setItem(4, 5, ItemStackBuilder.of(Material.HOPPER)
                .name("&b&lDOBIERZ CAlY LIMIT")
                .lore(
                        "",
                        " &7Kliknij tutaj aby &fdobrac caly &7limit!"
                ).asGuiItem(event -> {

                    for (DepositItem depositItem : this.plugin.getDepositItemCache().getDepositItems()) {

                        if (!depositItem.isWithdrawAll()) continue;

                        int amountInInventory = InventoryUtil.countAmountForDeposit(player, depositItem.getItem().clone());
                        if (amountInInventory >= depositItem.getLimit() && !LocationUtil.isInSpawn(player.getLocation())) continue;

                        if (corePlayer.getAmountOfDepositItem(depositItem.getId()) <= 0) continue;

                        int i = depositItem.getLimit() - amountInInventory;

                        if (LocationUtil.isInSpawn(player.getLocation()))
                            i = depositItem.getItem().getMaxStackSize();

                        if (i > corePlayer.getAmountOfDepositItem(depositItem.getId()))
                            i = corePlayer.getAmountOfDepositItem(depositItem.getId());

                        corePlayer.removeFromDeposit(depositItem, i);

                        ItemStack toAdd = depositItem.getItem().clone();
                        toAdd.setAmount(i);

                        InventoryUtil.addItem(player, toAdd);
                    }
                    open(player, corePlayer);

                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

}
