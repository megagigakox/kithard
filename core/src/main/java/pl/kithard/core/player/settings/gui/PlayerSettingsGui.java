package pl.kithard.core.player.settings.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class PlayerSettingsGui {

    private final CorePlugin plugin;

    public PlayerSettingsGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lUstawienia"))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        int i = 8;
        for (PlayerSettings setting : PlayerSettings.values()) {
            if (!setting.isGui()) {
                continue;
            }

            i++;
            boolean status = corePlayer.isDisabledSetting(setting);
            gui.setItem(i, ItemBuilder.from(status ?  new ItemStack(351, 1, (short) 8) : new ItemStack(351, 1, (short) 10))
                    .name(TextUtil.component("&b" + setting.getName()))
                    .glow(!status)
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &8» &7Aktualny status: " + (status ? "&c✖ &7(&cwylaczone&7)" : "&a✔ &7(&awlaczone&7)"),
                            "",
                            " &7Kliknij aby " + (status ? "&awlaczyc" : "&cwylaczyc") + " &7ustawienie!"
                    )))
                    .asGuiItem(inventoryClickEvent -> {

                        if (status) {
                            corePlayer.removeDisableSetting(setting);
                        }
                        else {
                            corePlayer.addDisabledSetting(setting);
                        }
                        corePlayer.setNeedSave(true);
                        open(player);

                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

}
