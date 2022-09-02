package pl.kithard.core.kit.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.kit.Kit;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TimeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitGui {
    private final CorePlugin plugin;

    public KitGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Wybierz kit:"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        for (Kit kit : this.plugin.getKitCache().getKits()) {

            List<String> lore = new ArrayList<>();
            for (String kitLore : kit.getLore()) {
                lore.add(kitLore
                        .replace("{COOLDOWN}", kit.getCooldown())
                        .replace("{ACCESS}", player.hasPermission(kit.getPermission()) ? "&a✔ ON" : "&c✖ OFF"));
            }

            gui.setItem(kit.getGuiSlot(), ItemBuilder.from(kit.getIcon()).name(TextUtil.component("&b&l" + kit.getName()))
                    .lore(TextUtil.component(lore))
                    .asGuiItem(event -> openKit(player, kit)));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    public void openKit(Player player, Kit kit) {
        Gui gui = Gui.gui()
                .title(TextUtil.component(kit.getGuiName()))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        kit.getItems().forEach(kitItem -> gui.addItem(ItemBuilder.from(kitItem).asGuiItem()));

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        gui.setItem(5, 8, ItemBuilder.from(new ItemStack(Material.getMaterial(351), 1, (short) 10))
                .name(TextUtil.component("&b&lOdbierz zestaw."))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj &aaby odebrac &7zestaw!")))
                .asGuiItem(event -> {

                    if (!kit.isStatus()) {
                        TextUtil.message(player, "&8[&4&l!&8] &cTen zestaw jest wylaczony!");
                        player.closeInventory();
                        return;
                    }

                    if (player.hasPermission(kit.getPermission())) {

                        if (corePlayer.getTime(kit.getName()) < System.currentTimeMillis()) {
                            kit.getItems().forEach(kitItem -> InventoryUtil.addItem(player, kitItem));
                            gui.close(player);
                            corePlayer.getKitCooldowns().put(kit.getName(), TimeUtil.parseDateDiff(kit.getCooldown(), true));

                        } else {

                            TextUtil.message(player, "&8[&4&l!&8] &cTen zestaw mozesz odebrac dopiero za &4" + TimeUtil.formatTimeMillis(corePlayer.getTime(kit.getName()) - System.currentTimeMillis()));

                        }

                    } else {

                        TextUtil.insufficientPermission(player, kit.getPermission());

                    }

                }));


        gui.setItem(6, 5, ItemBuilder.from(GuiHelper.BACK_ITEM).asGuiItem(event -> open(player)));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }
}