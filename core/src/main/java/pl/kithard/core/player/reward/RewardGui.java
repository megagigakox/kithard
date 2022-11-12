package pl.kithard.core.player.reward;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.SkullCreator;
import pl.kithard.core.util.TextUtil;

public class RewardGui {

    private final CorePlugin plugin;

    public RewardGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&3&lNagroda"))
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(2, 5, ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ0MjMzN2JlMGJkY2EyMTI4MDk3ZjFjNWJiMTEwOWU1YzYzM2MxNzkyNmFmNWZiNmZjMjAwMDAwMTFhZWI1MyJ9fX0="))
                .name("&9&lDiscord")
                .lore(
                        "",
                        " &8Instrukcja odbierania:",
                        "  &8- &7Dolacz na naszego discorda&8: &fdsc.gg/kithard",
                        "  &8- &7Wejdz na kanal tesktowy o nazwie&8: &fnagroda",
                        "  &8- &7Po wejsciu na kanal wpisz komende &f/nagroda " + player.getName(),
                        " &8(Pamietaj, ze nagrode mozesz odebrac tylko raz!)",
                        "",
                        " &8Po odebraniu nagrody otrzymasz...",
                        "  &8- &73x &b&lMagiczne Skrzynki.",
                        "",
                        "&7Kliknij &flewym &7aby otrzymac zaproszenie do &bdiscorda&7."
                )
                .asGuiItem(inventoryClickEvent -> {

                    TextUtil.message(player, "&7Discord: &fhttps://discord.gg/JGxumR4bGJ");

                }));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }
}
