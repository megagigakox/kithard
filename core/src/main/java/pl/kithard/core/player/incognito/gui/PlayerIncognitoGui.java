package pl.kithard.core.player.incognito.gui;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.incognito.PlayerIncognito;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

public class PlayerIncognitoGui {

    private final CorePlugin plugin;

    public PlayerIncognitoGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, CorePlayer corePlayer) {

        PlayerIncognito playerIncognito = corePlayer.getPlayerIncognito();
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&3&lUstawienia incognito"))
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(10, ItemStackBuilder.of(GuiHelper.GRAY_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(13, ItemStackBuilder.of(GuiHelper.GRAY_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(16, ItemStackBuilder.of(GuiHelper.GRAY_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(11, ItemStackBuilder.of(getItem(playerIncognito.isTagIncognito()))
                .name("&7Ukrycie &3&ltagu gildii")
                .lore(
                        "",
                        " &7Aktualny status&8: " + (playerIncognito.isTagIncognito() ? "&awlaczone" : "&cwylaczone"),
                        "",
                        " &8Informacje:",
                        "  &7Ta funkcja pozwala &3ukryc &7tag twojej gildii",
                        "  &7aby inni gracze &bgo nie zobaczyli&7!"

                )
                .asGuiItem(event -> {

                    playerIncognito.setTagIncognito(!playerIncognito.isTagIncognito());
                    open(player, corePlayer);

                }));

        gui.setItem(12, ItemStackBuilder.of(getItem(playerIncognito.isNameIncognito()))
                .name("&7Ukrycie &3&ltwojego nicku")
                .lore(
                        "",
                        " &7Aktualny status&8: " + (playerIncognito.isNameIncognito() ? "&awlaczone" : "&cwylaczone"),
                        "",
                        " &8Informacje:",
                        "  &7Ta funkcja pozwala &3ukryc &7twoj nick",
                        "  &7aby inni gracze &bnie mogli &7cie rozpoznac!"
                )
                .asGuiItem(event -> {
                    playerIncognito.setNameIncognito(!playerIncognito.isNameIncognito());
                    open(player, corePlayer);
                }));

        gui.setItem(14, ItemStackBuilder.of(getItem(playerIncognito.isSkinIncognito()))
                .name("&7Ukrycie &3&ltwojego skina")
                .lore(
                        "",
                        " &7Aktualny status&8: " + (playerIncognito.isSkinIncognito() ? "&awlaczone" : "&cwylaczone"),
                        "",
                        " &8Informacje:",
                        "  &7Ta funkcja pozwala &3zmienic &7twojego",
                        "  &7skina aby inni gracze &bnie mogli &7cie rozpoznac!",
                        "  &7Pamietaj, ze ty &fbedziesz widzial &7caly czas twojego skina",
                        "  &7lecz inni gracze &fbeda widziec &7skina incognito."
                )
                .asGuiItem(event -> {

                    playerIncognito.setSkinIncognito(!playerIncognito.isSkinIncognito());
                    this.plugin.getPlayerIncognitoSerivce().changeSkin(corePlayer);
                    open(player, corePlayer);

                }));

        gui.setItem(15, ItemStackBuilder.of(getItem(playerIncognito.isPointsIncognito()))
                .name("&7Ukrycie &3&ltwoich punktow")
                .lore(
                        "",
                        " &7Aktualny status&8: " + (playerIncognito.isPointsIncognito() ? "&awlaczone" : "&cwylaczone"),
                        "",
                        " &8Informacje:",
                        "  &7Ta funkcja pozwala &3zmanipulowac &7twoimi",
                        "  &7punktami rankinogwymi!",
                        "  &7Po &bwlaczeniu &7twoje punkty beda dalej widoczne",
                        "  &7lecz z lekkim &fprzeklamaniem&7."
                )
                .asGuiItem(event -> {
                    playerIncognito.setPointsIncognito(!playerIncognito.isPointsIncognito());
                    this.plugin.getPlayerNameTagService().updateDummy(corePlayer);
                    open(player, corePlayer);
                }));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    ItemStack getItem(boolean status) {
        return status
                ? ItemStackBuilder.of(new ItemStack(351, 1, (short) 10)).asItemStack()
                : ItemStackBuilder.of(new ItemStack(351, 1, (short) 1)).asItemStack();
    }
}
