package pl.kithard.core.player.backup.gui;

import com.mongodb.client.model.Filters;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.backup.PlayerBackup;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerBackupGui {

    private final CorePlugin plugin;

    public PlayerBackupGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player viewer, CorePlayer target) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {

            PaginatedGui gui = Gui.paginated()
                    .title(TextUtil.component("&7Backupy gracza: &3" + target.getName()))
                    .rows(6)
                    .pageSize(28)
                    .create();

            GuiHelper.fillColorGui6(gui);

            gui.setItem(6,7, ItemStackBuilder.of(GuiHelper.NEXT_ITEM)
                    .asGuiItem(inventoryClickEvent -> gui.next()));

            gui.setItem(6,3, ItemStackBuilder.of(GuiHelper.PREVIOUS_ITEM)
                    .asGuiItem(inventoryClickEvent -> gui.previous()));

            long currentTimeMillis = System.currentTimeMillis();
            List<PlayerBackup> backups = new ArrayList<>();
            for (Document document : this.plugin.getMongoService()
                    .getMongoClient()
                    .getDatabase("core")
                    .getCollection("player-backups")
                    .find(Filters.all("playerUuid", target.getUuid().toString()))) {

                backups.add(this.plugin.getGson().fromJson(document.toJson(), PlayerBackup.class));
            }

            backups.sort((o1, o2) -> Long.compare(o2.getDate(), o1.getDate()));
            int index = backups.size();
            for (PlayerBackup playerBackup : backups) {
                index--;

                ItemStackBuilder guiItem = ItemStackBuilder.of(playerBackup.getType().getIcon())
                        .name("&7Backup: &8&l#&e&l" + index)
                        .lore(
                                " &7Unikalne ID: &f" + playerBackup.getUuid(),
                                " &7Gracz: &f" + target.getName() + " &8(&f" + playerBackup.getPlayerUuid() + "&8)",
                                " &7Powod: &f" + playerBackup.getType().toString(),
                                " &7Zabojca: &f" + playerBackup.getKiller(),
                                " &7Data: &f" + TimeUtil.formatTimeMillisToDate(playerBackup.getDate()),
                                " &7Ping: &f" + (playerBackup.getPing() > 100 ? "&c" + playerBackup.getPing() : "&a" + playerBackup.getPing()),
                                " &7TPS Serwera: &f" + playerBackup.getTps(),
                                " &7Stracone punkty: &f" + playerBackup.getLostPoints(),
                                " ",
                                " &7Przywrocono: &f" + playerBackup.getAdminsRestored().size() + " razy",
                                " &7Przywrocony przez: "
                        );

                if (playerBackup.getAdminsRestored().isEmpty()) {
                    guiItem.appendLore("  &8- &eNikt nie oddal tego backupa.");
                }

                for (Map.Entry<Long, String> entry : playerBackup.getAdminsRestored().entrySet()) {
                    guiItem.appendLore("  &8- &e" + entry.getValue() + " &8(&f" + TimeUtil.formatTimeMillisToDate(entry.getKey()) + "&8)");
                }

                guiItem
                        .appendLore(" ")
                        .appendLore(" &7Kliknij &flewym &7aby zobaczyc podglad")
                        .appendLore(" &7Kliknij &fprawym &7aby usunac");


                gui.addItem(ItemStackBuilder.of(guiItem.asItemStack()).asGuiItem(inventoryClickEvent -> {

                    if (inventoryClickEvent.isLeftClick()) {

                        previewInventory(viewer, target, playerBackup);

                    } else {

                        TextUtil.message(viewer,
                                "&8(&2&l!&8) &aPomyslnie usunieto kopie zapasowa gracza &2" + target.getName() + " &8(&a"  +
                                        TimeUtil.formatTimeMillisToDate(playerBackup.getDate()) + "&8, &a" + playerBackup.getUuid() + "&8)");

                        this.plugin.getMongoService().delete(playerBackup);
                        viewer.closeInventory();
                        open(viewer, target);

                    }
                }));
            }
            TextUtil.message(viewer, "&8(&2&l!&8) &aBackupy zostaly zaladowane z bazy danych oraz wyswietlone w czasie: &2" + (System.currentTimeMillis() - currentTimeMillis) + "ms");

            Bukkit.getScheduler().runTask(plugin, () -> {
                gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
                gui.setCloseGuiAction(inventoryCloseEvent -> backups.clear());
                gui.open(viewer);
            });

        });
    }

    public void previewInventory(Player viewer, CorePlayer target, PlayerBackup playerBackup) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Backup: &3" + TimeUtil.formatTimeMillisToDate(playerBackup.getDate())))
                .rows(6)
                .create();

        GuiItem invPreview = ItemStackBuilder.of(Material.CHEST)
                .name("&bPodglad inventory")
                .asGuiItem();
        GuiItem armorPreview = ItemStackBuilder.of(Material.ARMOR_STAND)
                .name("&bPodglad armora")
                .asGuiItem(inventoryClickEvent -> previewArmor(viewer, target, playerBackup));

        gui.setItem(6,1, invPreview);
        gui.setItem(6,2, armorPreview);

        gui.setItem(6,8, ItemStackBuilder.of(new ItemStack(351, 1, (short) 10))
                .name("&7Oddaj backupa graczowi &f" + target.getName())
                .lore(
                        "",
                        " &7Kliknij &fprawym &7aby oddac armor + inventory.",
                        " &7Kliknij &flewym &7aby oddac armor + inventory + stracone punkty"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (target.source() == null) {
                        TextUtil.message(viewer, "gracz null");
                        return;
                    }

                    if (inventoryClickEvent.getClick() == ClickType.LEFT) {
                        this.plugin.getPlayerBackupService().restoreBackup(viewer, target.source(), playerBackup, true);
                    }
                    else if (inventoryClickEvent.getClick() == ClickType.RIGHT) {
                        this.plugin.getPlayerBackupService().restoreBackup(viewer, target.source(), playerBackup, false);
                    }

                    viewer.closeInventory();


                }));

        gui.setItem(6, 9, ItemStackBuilder.of(new ItemStack(351,1, (short) 1))
                .name("&cPowrot")
                .asGuiItem(inventoryClickEvent -> open(viewer, target)));

        GuiItem glass = ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem();

        gui.setItem(36, glass);
        gui.setItem(37, glass);
        gui.setItem(38, glass);
        gui.setItem(39, glass);
        gui.setItem(40, glass);
        gui.setItem(41, glass);
        gui.setItem(42, glass);
        gui.setItem(43, glass);
        gui.setItem(44, glass);

        gui.setItem(47, glass);
        gui.setItem(48, glass);
        gui.setItem(49, glass);
        gui.setItem(50, glass);
        gui.setItem(51, glass);

        for (ItemStack is : playerBackup.getInventory()) {
            if (is == null) {
                continue;
            }

            gui.addItem(ItemBuilder.from(is).asGuiItem());
        }

        gui.open(viewer);
        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
    }

    public void previewArmor(Player viewer, CorePlayer target, PlayerBackup playerBackup) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Backup: &3" + TimeUtil.formatTimeMillisToDate(playerBackup.getDate())))
                .rows(6)
                .create();

        GuiItem invPreview = ItemStackBuilder.of(Material.CHEST).name("&bPodglad inventory")
                .asGuiItem(inventoryClickEvent -> previewInventory(viewer, target, playerBackup));
        GuiItem armorPreview = ItemStackBuilder.of(Material.ARMOR_STAND).name("&bPodglad armora")
                .asGuiItem();

        gui.setItem(6,1, invPreview);
        gui.setItem(6,2, armorPreview);

        gui.setItem(6,8, ItemStackBuilder.of(new ItemStack(351, 1, (short) 10))
                .name("&7Oddaj backupa graczowi &f" + target.getName())
                .lore(
                        "",
                        " &7Kliknij &fprawym &7aby oddac armor + inventory.",
                        " &7Kliknij &flewym &7aby oddac armor + inventory + stracone punkty"
                )
                .asGuiItem(inventoryClickEvent -> {

                    if (target.source() == null) {
                        TextUtil.message(viewer, "gracz null");
                        return;
                    }

                    if (inventoryClickEvent.getClick() == ClickType.LEFT) {
                        this.plugin.getPlayerBackupService().restoreBackup(viewer, target.source(), playerBackup, true);
                    }
                    else if (inventoryClickEvent.getClick() == ClickType.RIGHT) {
                        this.plugin.getPlayerBackupService().restoreBackup(viewer, target.source(), playerBackup, false);
                    }

                    open(viewer, target);

                }));

        gui.setItem(6, 9, ItemBuilder.from(new ItemStack(351,1, (short) 1))
                .name(TextUtil.component("&cPowrot"))
                .asGuiItem(inventoryClickEvent -> open(viewer, target)));

        GuiItem glass = ItemBuilder.from(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem();

        gui.setItem(36, glass);
        gui.setItem(37, glass);
        gui.setItem(38, glass);
        gui.setItem(39, glass);
        gui.setItem(40, glass);
        gui.setItem(41, glass);
        gui.setItem(42, glass);
        gui.setItem(43, glass);
        gui.setItem(44, glass);

        gui.setItem(47, glass);
        gui.setItem(48, glass);
        gui.setItem(49, glass);
        gui.setItem(50, glass);
        gui.setItem(51, glass);

        for (ItemStack is : playerBackup.getArmor()) {
            if (is == null) {
                continue;
            }

            gui.addItem(ItemBuilder.from(is).asGuiItem());
        }

        gui.open(viewer);
        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
    }

}
