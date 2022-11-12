package pl.kithard.core.player.home.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class PlayerHomeGui {

    private final CorePlugin plugin;

    public PlayerHomeGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lZarzadzanie domami"))
                .rows(3)
                .create();

        gui.getFiller().fillTop(ItemBuilder.from(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());
        gui.getFiller().fillBottom(ItemBuilder.from(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(
                Arrays.asList(10, 12, 14, 16),
                ItemBuilder.from(GuiHelper.BLUE_STAINED_GLASS_PANE
                ).asGuiItem());

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        for (PlayerHome home : corePlayer.getHomes()) {

            GuiItem item;

            if (!player.hasPermission("kithard.homes." + home.getId())) {
                item = ItemBuilder.from(new ItemStack(351, 1, (short) 1))
                        .name(TextUtil.component("&7Dom: &8&l#&e&l" + home.getId()))
                        .lore(TextUtil.component(Arrays.asList(
                                "&cBlad! Nie masz uprawnien do tego domu!",
                                "",
                                "&7Zeby uzyskac do niego dostep",
                                "&7zakup minimalnie range " + this.plugin.getConfig().getString("prefix." + PlayerHome.REQUIRED_RANK.get(home.getId()))
                        )))
                        .asGuiItem();

            } else if (home.getLocation() == null) {
                item = ItemBuilder.from(new ItemStack(351, 1, (short) 7))
                        .name(TextUtil.component("&7Dom: &8&l#&e&l" + home.getId()))
                        .lore(TextUtil.component(Arrays.asList(
                                "&cBlad! Nie posiadasz tutaj domu!",
                                "",
                                "&7Zeby stworzyc nowy kliknij &fprawym &7przyciskiem myszy&7."
                        )))
                        .asGuiItem(event -> {

                            if (event.getClick() == ClickType.RIGHT) {
                                corePlayer.setHome(home.getId(), LocationUtil.toCenter(player.getLocation()));
                                corePlayer.setNeedSave(true);
                                TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie stworzono domek!");
                                open(player);
                            }

                        });

            } else {
                item = ItemBuilder.from(new ItemStack(351, 1, (short) 10))
                        .name(TextUtil.component("&7Dom: &8&l#&e&l" + home.getId()))
                        .lore(TextUtil.component(Arrays.asList(
                                "&7Posiadasz juz tutaj dom!",
                                "",
                                "&7Kliknij &flewym &7aby sie teleportowac.",
                                "&7Kliknij &fprawym &7aby ustawic nowa lokacje domu. "
                        )))
                        .asGuiItem(event -> {

                            if (event.getClick() == ClickType.RIGHT) {
                                corePlayer.setHome(home.getId(), player.getLocation());
                                corePlayer.setNeedSave(true);
                                TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie stworzono domek!");
                                open(player);
                            }
                            else if (event.getClick() == ClickType.LEFT) {

                                Guild guild = this.plugin.getGuildCache().findByLocation(home.getLocation());
                                if (guild != null && !guild.isMember(player.getUniqueId())) {
                                    TextUtil.message(player, "&8(&4&l!&8) &cTen dom jest na terenie innej gildii!");
                                    return;
                                }

                                corePlayer.teleport(home.getLocation(), 5);
                            }

                        });
            }


            gui.addItem(item);

        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }
}