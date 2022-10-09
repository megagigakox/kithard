package pl.kithard.core.settings.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

public class ServerSettingGui {

    private final CorePlugin plugin;

    public ServerSettingGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {

        PaginatedGui gui = Gui.paginated()
                .title(TextUtil.component("&3&lZarzadzanie serwerem"))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(3, 3, ItemStackBuilder.of(GuiHelper.BACK_ITEM).asGuiItem(inventoryClickEvent -> gui.previous()));
        gui.setItem(3, 7, ItemStackBuilder.of(GuiHelper.BACK_ITEM).asGuiItem(inventoryClickEvent -> gui.next()));

        for (ServerSettingsType value : ServerSettingsType.values()) {
            boolean status = this.plugin.getServerSettings().isEnabled(value);
            gui.addItem(ItemStackBuilder.of(value.getIcon())
                    .name("&b" + value.getName())
                    .lore(
                            "",
                            " &8» &7Aktualny status: " + (status ? "&a✔ &7(&awlaczone&7)" : "&c✖ &7(&cwylaczone&7)"),
                            "",
                            " &7Kliknij aby " + (status ? "&cwylaczyc" : "&awlaczyc") + " &7ustawienie!"
                    )
                    .glow(status)
                    .asGuiItem(inventoryClickEvent -> {

                        if (status) {
                            this.plugin.getServerSettings().removeEnabledSetting(value);
                        }
                        else {
                            this.plugin.getServerSettings().addEnabledSetting(value);
                        }

                        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
                                this.plugin.getServerSettingsConfiguration().save());
                        open(player);

                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

}
