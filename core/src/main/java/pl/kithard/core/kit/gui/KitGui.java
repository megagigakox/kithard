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
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

public class KitGui {
    private final CorePlugin plugin;

    public KitGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lWybor zestawu"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        for (Kit kit : this.plugin.getKitConfiguration().getKits().values()) {

            gui.setItem(kit.getGuiSlot(), ItemStackBuilder.of(kit.getIcon()).name("&3&l" + kit.getName())
                    .lore(
                            "",
                            " &7Status&8: " + (kit.isEnable() ? "&a✔ wlaczony." : "&c✖ wylaczony."),
                            " &7Cooldown tego zestawu wynosi&8: &f" + TimeUtil.formatTimeMillis(kit.getCooldown()) + ".",
                            " &7Posiadasz dostep&8: " + (player.hasPermission(kit.getPermission()) ? "&a✔ tak." : "&c✖ nie."),
                            "",
                            "&7Kliknij &flewym &7aby zobaczyc &3podglad &7tego zestawu."
                    )
                    .asGuiItem(event -> openKit(player, kit)));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    public void openKit(Player player, Kit kit) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lZestaw: &8(" + kit.getName() + "&8)"))
                .rows(6)
                .create();

        GuiHelper.fillColorGui6(gui);

        kit.getItems().forEach(kitItem -> gui.addItem(ItemBuilder.from(kitItem).asGuiItem()));

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        long time = corePlayer.getCooldown().getKitCooldown(kit.getName());

        ItemStackBuilder builder = ItemStackBuilder.of(new ItemStack(Material.getMaterial(351), 1, (short) 10))
                .name("&b&lOdbierz zestaw.");

        if (player.hasPermission(kit.getPermission())) {
            builder.appendLore("");
            builder.appendLore(" &7Mozesz odebrac&8: " + (time > System.currentTimeMillis() ? "&fza" + TimeUtil.formatTimeMillis(time - System.currentTimeMillis()) + "." : "&a✔ w tym momencie."));
            builder.appendLore("");
            builder.appendLore("&7Kliknij &flewym &7aby &3odebrac &7ten zestaw.");
        }
        else {
            builder.appendLore("&cBlad! Nie posiadasz uprawnien do odebrania tego zestawu!");
        }

        gui.setItem(5, 8, builder.asGuiItem(event -> {

            if (!kit.isEnable()) {
                TextUtil.message(player, "&8(&4&l!&8) &cTen zestaw jest wylaczony!");
                player.closeInventory();
                return;
            }

            if (player.hasPermission(kit.getPermission())) {

                if (corePlayer.getCooldown().getKitCooldown(kit.getName()) < System.currentTimeMillis()) {
                    kit.getItems().forEach(kitItem -> InventoryUtil.addItem(player, kitItem));
                    gui.close(player);
                    corePlayer.getCooldown().getKitCooldowns().put(kit.getName(), kit.getCooldown() + System.currentTimeMillis());

                } else {

                    TextUtil.message(player, "&8(&4&l!&8) &cTen zestaw mozesz odebrac dopiero za &4" +
                            TimeUtil.formatTimeMillis(corePlayer.getCooldown().getKitCooldown(kit.getName()) - System.currentTimeMillis()));

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