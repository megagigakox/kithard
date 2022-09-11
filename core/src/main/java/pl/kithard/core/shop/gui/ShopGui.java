package pl.kithard.core.shop.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.shop.ShopUtil;
import pl.kithard.core.shop.item.ShopItem;
import pl.kithard.core.shop.item.ShopItemType;
import pl.kithard.core.shop.item.ShopVillager;
import pl.kithard.core.shop.item.ShopVillagerItem;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.*;

public class ShopGui {

    private final CorePlugin plugin;

    public ShopGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            Gui gui = Gui.gui()
                    .title(TextUtil.component("&3&lSklep"))
                    .rows(5)
                    .create();

            GuiHelper.fillColorGui5(gui);
            this.prepareTops(gui);

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

            gui.setItem(3, 3, ItemBuilder.from(new ItemStack(351, 1, (short) 10))
                    .name(TextUtil.component("&a&lKupno Itemkow"))
                    .enchant(Enchantment.DURABILITY, 3)
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &8» &7Kliknij &faby zobaczyc &7oferte!")))
                    .glow(true)
                    .asGuiItem(event -> {

                        if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.SHOP) && !player.hasPermission("shop.bypass")) {
                            TextUtil.message(player, "&cChwilowo wylaczone!");
                            player.closeInventory();
                            return;
                        }

                        openBuy(player);
                    }));

            gui.setItem(3, 5, ItemBuilder.from(new ItemStack(351, 1, (short) 1))
                    .name(TextUtil.component("&c&lSprzedaz Itemkow"))
                    .enchant(Enchantment.DURABILITY, 3)
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &8» &7Kliknij &faby zobaczyc &7oferte!")))
                    .glow(true)
                    .asGuiItem(inventoryClickEvent -> openSell(player)));

            gui.setItem(3, 7, ItemBuilder.from(new ItemStack(383, 1, (short) 120))
                    .name(TextUtil.component("&2&lVillager"))
                    .enchant(Enchantment.DURABILITY, 3)
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &8» &7Kliknij &faby zobaczyc &7oferte!")))
                    .glow(true)
                    .asGuiItem(inventoryClickEvent -> {

                        if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.SHOP) && !player.hasPermission("shop.bypass")) {
                            TextUtil.message(player, "&cChwilowo wylaczone!");
                            player.closeInventory();
                            return;
                        }

                        openVillagers(player);
                    }));

            gui.setItem(1,5, ItemBuilder.from(Material.PAPER)
                    .name(TextUtil.component(" "))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Twoj stan konta: &b" + corePlayer.getMoney()
                    )))
                    .asGuiItem());

            Bukkit.getScheduler().runTask(this.plugin, () -> {
                gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
                gui.open(player);
            });

        });

    }

    public void openBuy(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lSklep &8(kupno)"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));

        for (ShopItem buyItem : this.plugin.getShopConfiguration().getItems()) {
            if (buyItem.getType() != ShopItemType.BUY) {
                continue;
            }

            gui.addItem(ItemStackBuilder.of(buyItem.getItem().clone())
                    .appendLore(
                            "",
                            "  &7Po zakupie otrzymasz...",
                            "  &8» &f&l" + buyItem.getName() + "&8, &f&l" + buyItem.getItem().getAmount() + "x &8- &3&l" + buyItem.getPrice() + "zl",
                            "",
                            "  &fAby zakupic...",
                            "  &8» &aNacisnij na ten przedmiot!",
                            "",
                            "&7Twoj aktualny stan konta wynosi&8: &3" + corePlayer.getMoney())
                    .asGuiItem(inventoryClickEvent -> {

                        if (corePlayer.getMoney() >= buyItem.getPrice()) {
                            InventoryUtil.addItem(player, buyItem.getItem());
                            corePlayer.setMoney(corePlayer.getMoney() - buyItem.getPrice());
                            corePlayer.setSpendMoney(corePlayer.getSpendMoney() + buyItem.getPrice());
                            corePlayer.setNeedSave(true);

                            for (String a : buyItem.getCommands()) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), a.replace("{PLAYER}", player.getName()));
                            }

                            openBuy(player);

                        } else {
                            TextUtil.message(player, "&8[&4&l!&8] &cNie posiadasz &4wystarczajacej ilosci pieniedzy &caby zakupic ten przedmiot!");
                        }

                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openSell(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Sklep (sprzedaz)"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));

        for (ShopItem sellItem : this.plugin.getShopConfiguration().getItems()) {
            if (sellItem.getType() != ShopItemType.SELL) {
                continue;
            }

            gui.addItem(ItemStackBuilder.of(sellItem.getItem().clone())
                    .appendLore(
                            "",
                            "  &7Oferta sprzedazy...",
                            "  &8» &f&l" + sellItem.getName() + "&8, &f&l" + sellItem.getItem().getAmount() + "x &8- &3&l" + sellItem.getPrice() + "zl",
                            "",
                            "  &fAby sprzedac...",
                            "  &8» &cNacisnij na ten przedmiot!",
                            "",
                            (corePlayer.isDisabledSellItem(sellItem) ? " &7Ten item &a&nnie jest &7w liscie sprzedazy wiec.." : " &7Ten item &c&njest w liscie &7sprzedaży wiec.."),
                            (corePlayer.isDisabledSellItem(sellItem) ? " &a&lNie sprzedasz &7go uzywajac sprzedazy &c&nwszystkiego!" : " &c&lSprzedasz go &7uzywajac sprzedazy &c&nwszystkiego!"),
                            "          &8(Kliknij prawym aby to zmienic!)")
                    .asGuiItem(event -> {

                        if (event.getClick() == ClickType.LEFT) {
                            if (InventoryUtil.hasItem(player, sellItem.getItem().getType(), sellItem.getItem().getAmount())) {
                                InventoryUtil.removeItem(player, sellItem.getItem().getType(), sellItem.getItem().getAmount());
                                corePlayer.setMoney(corePlayer.getMoney() + sellItem.getPrice());
                                corePlayer.setEarnedMoney(corePlayer.getEarnedMoney() + sellItem.getPrice());
                                corePlayer.setNeedSave(true);
                                openSell(player);
                            } else {
                                TextUtil.message(player, "&8[&4&l!&8] &cNie posiadasz tego itemku na sprzedaz!");
                            }
                        } else if (event.getClick() == ClickType.RIGHT) {
                            if (corePlayer.isDisabledSellItem(sellItem)) {
                                corePlayer.removeDisabledSellItem(sellItem);
                            } else {
                                corePlayer.addDisabledSellItem(sellItem);
                            }
                            openSell(player);
                        }
                    }));
        }

        gui.setItem(5,5, ItemBuilder.from(Material.HOPPER)
                .name(TextUtil.component("&c&lSprzedaż Wszystkiego"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &8» &7Kliknij tutaj aby &asprzedac wszystkie &7przedmioty z eq!",
                        "   &8(Ta funkcja sprzedaje tylko przedmioty...",
                        "        &8ktore nie sa zignorowane!",
                        "    &8Sprawdz to najeżdzajac na itemki u gory!)")))
                .asGuiItem(event -> {
                    ShopUtil.sellAll(player, corePlayer, plugin);
                }));


            gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openVillagers(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lSklep &8(kupno za emeraldy)"))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(3, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));

        for (ShopVillager villager : this.plugin.getShopConfiguration().getVillagers()) {
            gui.addItem(ItemStackBuilder.of(villager.getIcon().clone())
                    .name("&3&l" + villager.getName())
                    .lore(
                            "",
                            " &7Kliknij &flewym &7aby zobaczyc oferte!")
                    .asGuiItem(inventoryClickEvent -> openVillagerItems(player, villager)));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openVillagerItems(Player player, ShopVillager shopVillager) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lVillager &8(" + shopVillager.getName() + "&8)"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> openVillagers(player)));

        for (ShopVillagerItem villagerItem : shopVillager.getItems()) {
            gui.addItem(ItemStackBuilder.of(villagerItem.getItem().clone())
                    .appendLore(
                            "",
                            "  &7Po zakupie otrzymasz...",
                            "  &8» &f&l" + villagerItem.getName() + "&8, &f&l" + villagerItem.getItem().getAmount() + "x &8- &3&l" + villagerItem.getPrice() + " blokow eme", "",
                            "  &fAby zakupic...",
                            "  &8» &aNacisnij na ten przedmiot!")
                    .asGuiItem(event -> {

                        if (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), (int) villagerItem.getPrice())) {
                            player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, (int) villagerItem.getPrice()));
                            InventoryUtil.addItem(player, villagerItem.getItem());
                        }
                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    private void prepareTops(Gui gui) {
        ItemStack topMoney = ItemBuilder.from(new ItemStack(351, 1, (short) 14))
                .name(TextUtil.component(" &6&lTopka posiadanego hajsu!")).build();

        ItemStack topSpendMoney = ItemBuilder.from(new ItemStack(351, 1, (short) 6))
                .name(TextUtil.component(" &3&lTopka wydanego hajsu!")).build();

        ItemStack topEarnedMoney = ItemBuilder.from(new ItemStack(351, 1, (short) 11))
                .name(TextUtil.component(" &e&lTopka zarobionego hajsu!")).build();

        List<CorePlayer> topMoneyPlayers = new ArrayList<>(this.plugin.getCorePlayerCache().getValues());
        topMoneyPlayers.sort((o1, o2) -> Double.compare(o2.getMoney(), o1.getMoney()));

        List<String> lore1 = new ArrayList<>();
        ItemMeta meta1 = topMoney.getItemMeta();

        int i = 0;
        for (CorePlayer corePlayer : topMoneyPlayers) {
            i++;

            if (i > 10) {
                break;
            }

            lore1.add(" &7" + i + ". &f" + corePlayer.getName() + " &8- &7posiada: &6" + corePlayer.getMoney() + " zl");
        }

        meta1.setLore(TextUtil.color(lore1));
        topMoney.setItemMeta(meta1);
        gui.setItem(5,4, ItemBuilder.from(topMoney).asGuiItem());

        List<CorePlayer> topSpendMoneyPlayers = new ArrayList<>(this.plugin.getCorePlayerCache().getValues());
        topSpendMoneyPlayers.sort((o1, o2) -> Double.compare(o2.getSpendMoney(), o1.getSpendMoney()));

        List<String> lore2 = new ArrayList<>();
        ItemMeta meta2 = topSpendMoney.getItemMeta();

        int a = 0;
        for (CorePlayer corePlayer : topSpendMoneyPlayers) {
            a++;

            if (a > 10) {
                break;
            }

            lore2.add(" &7" + a + ". &f" + corePlayer.getName() + " &8- &7wydal: &3" + corePlayer.getSpendMoney() + " zl");
        }

        meta2.setLore(TextUtil.color(lore2));
        topSpendMoney.setItemMeta(meta2);
        gui.setItem(5,6, ItemBuilder.from(topSpendMoney).asGuiItem());

        List<CorePlayer> topEarnedMoneyPlayers = new ArrayList<>(this.plugin.getCorePlayerCache().getValues());
        topEarnedMoneyPlayers.sort((o1, o2) -> Double.compare(o2.getEarnedMoney(), o1.getEarnedMoney()));

        List<String> lore3 = new ArrayList<>();
        ItemMeta meta3 = topEarnedMoney.getItemMeta();

        int b = 0;
        for (CorePlayer corePlayer : topEarnedMoneyPlayers) {
            b++;

            if (b > 10) {
                break;
            }

            lore3.add(" &7" + b + ". &f" + corePlayer.getName() + " &8- &7zarobil: &e" + corePlayer.getEarnedMoney() + " zl");
        }

        meta3.setLore(TextUtil.color(lore3));
        topEarnedMoney.setItemMeta(meta3);
        gui.setItem(5,5, ItemBuilder.from(topEarnedMoney).asGuiItem());
        gui.setCloseGuiAction(event -> {

            topSpendMoneyPlayers.clear();
            topEarnedMoneyPlayers.clear();
            topMoneyPlayers.clear();

        });
    }
}
