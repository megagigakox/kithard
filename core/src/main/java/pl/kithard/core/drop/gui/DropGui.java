package pl.kithard.core.drop.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.drop.special.SpecialDropItem;
import pl.kithard.core.drop.special.SpecialDropItemType;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.drop.util.DropUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.Arrays;

public class DropGui {

    private final CorePlugin plugin;

    public DropGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Wybierz podglad dropu:"))
                .rows(5)
                .create();

        GuiHelper.fillColorGui5(gui);

        gui.setItem(3, 5, ItemBuilder.from(Material.DIAMOND_PICKAXE)
                .name(TextUtil.component("&7Drop z &8kamienia&7!"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij aby &fprzejsc &7dalej!"
                )))
                .asGuiItem(inventoryClickEvent -> openStone(player)));

        gui.setItem(3, 7, ItemBuilder.from(Material.CHEST)
                .name(TextUtil.component("&7Drop z &bmagicznych skrzynek&7!"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij aby &fprzejsc &7dalej!"
                )))
                .asGuiItem(inventoryClickEvent -> openMagicChest(player)));

        gui.setItem(3, 3, ItemBuilder.from(Material.MOSSY_COBBLESTONE)
                .name(TextUtil.component("&7Drop z &2cobblex&7!"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij aby &fprzejsc &7dalej!"
                )))
                .asGuiItem(inventoryClickEvent -> openCobbleX(player)));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openStone(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Drop z &8kamienia&7:"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        ServerSettings serverSettings = this.plugin.getServerSettings();

        for (DropItem dropItem : this.plugin.getDropItemCache().getDropItems()) {
            boolean status = corePlayer.isDisabledDropItem(dropItem);
            boolean fortune = dropItem.isFortune();
            double chance = DropUtil.calculateChanceFromStone(dropItem, corePlayer, serverSettings);
            int numberOfMinedDrops = corePlayer.getNumberOfMinedDropItem(dropItem.getName());

            gui.addItem(ItemBuilder.from(dropItem.getItem().getType())
                    .name(TextUtil.component("&3&l" + dropItem.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Szansa: &b" + MathUtil.round(chance, 3) + "% &7(&f+" + MathUtil.round(chance - dropItem.getChance() ,3) + "%&7)",
                            " &7Wykopales: &b" + numberOfMinedDrops + " &7razy!",
                            " &7Fortuna: " + (fortune ? "&a✔" : "&c✖"),
                            " &7Aktualny status: " + (status ? "&c✖" : "&a✔"),
                            "",
                            " &7Posiadajac range &6SVIP&7, &bMVP &7lub &5Legenda",
                            " &7twoj drop zostaje zwiekszony!",
                            "",
                            " &7Kliknij aby " + (status ? "&awlaczyc" : "&cwylaczyc") + " &7drop!"
                    )))
                    .glow(!status)
                    .asGuiItem(inventoryClickEvent -> {

                        if (status) {
                            corePlayer.removeDisabledDropItem(dropItem);
                        } else {
                            corePlayer.addDisabledDropItem(dropItem);
                        }

                        corePlayer.setNeedSave(true);
                        openStone(player);
                    }));
        }

        boolean cobbleStatus = !corePlayer.isDisabledSetting(PlayerSettings.COBBLE_DROP);
        gui.setItem(5, 8, ItemBuilder.from(Material.COBBLESTONE)
                .name(TextUtil.component("&bCobblestone"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &8» &7Aktualny status: " + (cobbleStatus ? "&a✔" : "&c✖"),
                        "",
                        " &7Kliknij aby " + (cobbleStatus ? "&cwylaczyc" : "&awlaczyc") + " &7drop!"
                )))
                .glow(cobbleStatus)
                .asGuiItem(inventoryClickEvent -> {

                    if (cobbleStatus) {
                        corePlayer.addDisabledSetting(PlayerSettings.COBBLE_DROP);
                    } else {
                        corePlayer.removeDisableSetting(PlayerSettings.COBBLE_DROP);
                    }

                    corePlayer.setNeedSave(true);

                    openStone(player);

                }));

        boolean messagesStatus = !corePlayer.isDisabledSetting(PlayerSettings.DROP_MESSAGES);
        gui.setItem(5, 7, ItemBuilder.from(Material.PAPER)
                .name(TextUtil.component("&bPowiadomienia o dropie"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &8» &7Aktualny status: " + (messagesStatus ? "&a✔" : "&c✖"),
                        "",
                        " &7Kliknij aby " + (messagesStatus ? "&cwylaczyc" : "&awlaczyc") + " &7wiadomosci!"
                )))
                .glow(messagesStatus)
                .asGuiItem(inventoryClickEvent -> {

                    if (messagesStatus) {
                        corePlayer.addDisabledSetting(PlayerSettings.DROP_MESSAGES);
                    } else {
                        corePlayer.removeDisableSetting(PlayerSettings.DROP_MESSAGES);
                    }

                    corePlayer.setNeedSave(true);
                    openStone(player);

                }));

        boolean globalTurboDrop = serverSettings.getTurboDrop() > System.currentTimeMillis();
        boolean playerTurboDrop = corePlayer.getTurboDrop() > System.currentTimeMillis();
        gui.setItem(5, 6, ItemBuilder.from(Material.EXP_BOTTLE)
                .name(TextUtil.component("&bTURBODROP"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &8» &7Serwerowy TurboDrop: " + (globalTurboDrop ? "&a✔ &7(&a" + TimeUtil.formatTimeMillis(serverSettings.getTurboDrop() - System.currentTimeMillis()) + "&7)" : "&c✖"),
                        " &8» &7Twoj TurboDrop: " + (playerTurboDrop ? "&a✔ &7(&a" + TimeUtil.formatTimeMillis(corePlayer.getTurboDrop() - System.currentTimeMillis()) + "&7)" : "&c✖"),
                        ""
                        )))
                .glow(messagesStatus)
                .asGuiItem(inventoryClickEvent -> {

                }));

        gui.setItem(5, 2, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 2))
                .name(TextUtil.component("&aWlacz wszystkie dropy"))
                .asGuiItem(inventoryClickEvent -> {

                    for (DropItem dropItem : this.plugin.getDropItemCache().getDropItems()) {
                        corePlayer.removeDisabledDropItem(dropItem);
                    }

                    corePlayer.setNeedSave(true);
                    openStone(player);

                }));

        gui.setItem(5, 3, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 1))
                .name(TextUtil.component("&cWylacz wszystkie dropy"))
                .asGuiItem(inventoryClickEvent -> {

                    for (DropItem dropItem : this.plugin.getDropItemCache().getDropItems()) {
                        if (corePlayer.isDisabledDropItem(dropItem)) {
                            continue;
                        }

                        corePlayer.addDisabledDropItem(dropItem);

                    }
                    corePlayer.setNeedSave(true);
                    openStone(player);

                }));

        gui.setItem(5, 4, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 12))
                .name(TextUtil.component("&bWlacz dropy itemow na gildie"))
                .asGuiItem(inventoryClickEvent -> {

                    for (DropItem dropItem : this.plugin.getDropItemCache().getDropItems()) {
                        corePlayer.removeDisabledDropItem(dropItem);

                        if (!dropItem.isGuildItem()) {
                            corePlayer.addDisabledDropItem(dropItem);
                        }
                    }

                    corePlayer.setNeedSave(true);
                    openStone(player);

                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

    public void openMagicChest(Player player) {
        PaginatedGui gui = Gui.paginated()
                .title(TextUtil.component("&7Drop z &bmagicznych skrzynek&7:"))
                .pageSize(28)
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));

        gui.setItem(6,7, ItemBuilder.from(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(6,3, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));


        for (SpecialDropItem dropItem : this.plugin.getDropItemCache().getSpecialDropItems()) {

            if (dropItem.getType() != SpecialDropItemType.MAGIC_CHEST) {
                continue;
            }

            gui.addItem(ItemBuilder.from(dropItem.getItem())
                    .asGuiItem());

        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openCobbleX(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7lDrop z &2cobblex'ow&7:"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> open(player)));


        for (SpecialDropItem dropItem : this.plugin.getDropItemCache().getSpecialDropItems()) {

            if (dropItem.getType() != SpecialDropItemType.COBBLEX) {
                continue;
            }

            gui.addItem(ItemBuilder.from(dropItem.getItem())
                    .asGuiItem());

        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }
}
