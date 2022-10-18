package pl.kithard.core.guild.report;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

public class GuildReportGui {

    private final CorePlugin plugin;

    public GuildReportGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        PaginatedGui gui = Gui.paginated()
                .pageSize(28)
                .rows(6)
                .title(TextUtil.component("&3&lZgloszenia"))
                .create();

        GuiHelper.fillColorGui6(gui);

        gui.setItem(6,6, ItemBuilder.from(GuiHelper.NEXT_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.next()));

        gui.setItem(6,4, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM)
                .asGuiItem(inventoryClickEvent -> gui.previous()));

        int i = 0;
        for (GuildReport guildReport : this.plugin.getGuildReportCache().values()) {

            gui.addItem(ItemStackBuilder.of(Material.SIGN)
                    .name("&7Zgloszenie &8&l#&e&l" + i++)
                    .lore(
                            " &7Wyslane przez&8: &f" + guildReport.getSender(),
                            " &7Zgloszona gildia&8: &f" + guildReport.getGuild(),
                            " &7Powod&8: &f" + guildReport.getReason(),
                            " &7Data&8: &f" + TimeUtil.formatTimeMillisToDate(guildReport.getDate()),
                            "",
                            "&aKliknij tutaj aby usunac!"
                    )
                    .asGuiItem(event -> {

                        this.plugin.getGuildReportCache().remove(guildReport);
                        open(player);

                    }));

        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }
}
