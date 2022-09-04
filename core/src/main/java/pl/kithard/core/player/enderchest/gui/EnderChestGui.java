package pl.kithard.core.player.enderchest.gui;

import dev.triumphteam.gui.guis.Gui;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

public class EnderChestGui {

    private final CorePlugin plugin;

    public EnderChestGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player p, Player viewer) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&7Wybor enderchesta:"))
                .rows(3)
                .create();

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(p);
        GuiHelper.fillColorGui3(gui);

        int slot = 10;
        for(PlayerEnderChest enderChest : corePlayer.getEnderChests()) {
            slot++;

            boolean access = p.hasPermission(enderChest.getPermission());

            ItemStackBuilder item = ItemStackBuilder.of(access ? Material.ENDER_CHEST : Material.BARRIER)
                    .name("&7Enderchest: &8&l#&e&l" + enderChest.getId());

            if (!access) {
                item.lore(
                        "",
                        "&cBlad! Nie masz uprawnien do tego enderchesta!",
                        "",
                        "&7Zeby uzyskac do niego dostep",
                        "&7zakup minimalnie range " + this.plugin.getConfig().getString("prefix." + enderChest.getRequiredRank())
                );
            }
            else {

                item.lore(
                        "",
                        " &8» &7Opis: &f" + enderChest.getLore(),
                        "",
                        " &7Kliknij &flewym &7aby &3otworzyc &7enderchesta!",
                        " &7Kliknij &fprawym &7aby &3ustawic &7opis!"
                );
            }

            gui.setItem(slot, ItemStackBuilder.of(item.asItemStack()).asGuiItem(event -> {
                if (event.getClick() == ClickType.LEFT) {
                    if (p.hasPermission(enderChest.getPermission())) {
                        enderChest.openInventory(p);
                    } else {
                        viewer.closeInventory();
                        TextUtil.insufficientPermission(p, enderChest.getPermission());
                    }
                } else {
                    new AnvilGUI.Builder()
                            .onComplete((player, text) -> {
                                enderChest.setLore(text);
                                corePlayer.setNeedSave(true);
                                open(p, viewer);
                                return AnvilGUI.Response.close();
                            })
                            .text("Wprowadz opis")
                            .title("Wprowadz opis")
                            .plugin(plugin)
                            .open(viewer);
                }
            }));
        }

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(viewer);
    }

}
