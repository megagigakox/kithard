package pl.kithard.core.guild.log.gui;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

public class GuildLogGui {

    private final CorePlugin plugin;

    public GuildLogGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, Guild guild) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&3&lLogi gildyjne"))
                .create();

        GuiHelper.fillColorGui3(gui);

        gui.setItem(3,5, ItemStackBuilder.of(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> new GuildPanelGui(plugin).openPanel(player, guild)));

        int i = 8;
        for (GuildLogType type : GuildLogType.values()) {
            i++;

            gui.setItem(i, ItemStackBuilder.of(type.getIcon())
                            .name("&3&l" + type.getName())
                            .lore(
                                    "",
                                    " &7Kliknij aby &fprzejsc &7dalej!"
                            )
                    .asGuiItem(event -> openType(player, guild, type)));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    public void openType(Player player, Guild guild, GuildLogType logType) {
        PaginatedGui gui = Gui.paginated()
                .rows(6)
                .title(TextUtil.component("&7Kategoria&8: (&3" + logType + "&8)"))
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6,6, ItemStackBuilder.of(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(6,4, ItemStackBuilder.of(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));

        gui.setItem(6,5, ItemStackBuilder.of(GuiHelper.BACK_ITEM)
                .asGuiItem(inventoryClickEvent -> this.open(player, guild)));

        for (GuildLog guildLog : guild.getLogsByType(logType)) {

            gui.addItem(ItemStackBuilder.of(Material.PAPER)
                    .name("&e" + logType.getName())
                    .lore(
                            " &7Opis: " + guildLog.getAction(),
                            " &7Data: &f" + TimeUtil.formatTimeMillisToDate(guildLog.getDate())
                    )
                    .asGuiItem());

        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }
}
