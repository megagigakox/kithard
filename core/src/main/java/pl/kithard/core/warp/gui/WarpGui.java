package pl.kithard.core.warp.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.warp.Warp;

import java.util.Arrays;

public class WarpGui {

    private final CorePlugin plugin;

    public WarpGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lWarpy"))
                .rows(5)
                .create();

        GuiHelper.fillColorGui5(gui);

        int i = 19;
        for (Warp warp : this.plugin.getWarpCache().values()) {
            i++;

            gui.setItem(i, ItemBuilder.from(warp.getIcon()).glow()
                    .name(TextUtil.component("&7Warp: &b" + warp.getName()))
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7Kliknij tutaj aby &3rozpoczac &7teleportacje!"
                    )))
                    .asGuiItem(inventoryClickEvent -> {
                        gui.close(player);

                        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
                        corePlayer.teleport(warp.getLocation(), 10);

                    }));
        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

}
