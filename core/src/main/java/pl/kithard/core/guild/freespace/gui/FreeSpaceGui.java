package pl.kithard.core.guild.freespace.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class FreeSpaceGui {

    private final CorePlugin plugin;

    public FreeSpaceGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            PaginatedGui gui = Gui.paginated()
                    .rows(6)
                    .pageSize(28)
                    .title(TextUtil.component("&7Wolne miejsca:"))
                    .create();

            GuiHelper.fillColorGui6(gui);

            gui.setItem(6, 3, ItemBuilder.from(GuiHelper.PREVIOUS_ITEM).asGuiItem(event -> gui.previous()));
            gui.setItem(6, 7, ItemBuilder.from(GuiHelper.NEXT_ITEM).asGuiItem(event -> gui.next()));

            int i = 0;
            for (Location location : this.plugin.getFreeSpaceCache().values()
                    .stream()
                    .sorted(Comparator.comparingDouble(o -> LocationUtil.distance(o, player.getLocation())))
                    .collect(Collectors.toCollection(LinkedHashSet::new))) {
                i++;

                int distance = (int) LocationUtil.distance(player.getLocation(), location);

                gui.addItem(ItemBuilder.from(new ItemStack(159, 1, (short) 13))
                        .name(TextUtil.component("&7Wolne miejsce: &8&l#&e&l" + i))
                        .lore(TextUtil.component(Arrays.asList(
                                "",
                                " &7Kordy: &bx: &f" + location.getBlockX() + " &bz: &f" + location.getBlockZ(),
                                " &7Dystans: &b" + distance + "m",
                                " &7Koszt teleportacji: &b" + distance / 6 + " blokow emeraldow",
                                "",
                                "&7Kliknij &flewym &7aby sie teleportowac."
                        )))
                        .asGuiItem(inventoryClickEvent -> {

                            if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), distance / 6)) {
                                TextUtil.message(
                                        player,
                                        "&8[&4&l!&8] &cNie posiadasz wystarczajacej ilosci blokow emeraldow! &4(" + distance / 6 + " blokow)"
                                );
                                return;
                            }

                            InventoryUtil.removeItem(player, Material.EMERALD_BLOCK,distance / 6);
                            this.plugin.getCorePlayerCache().findByPlayer(player).teleport(location, 20);
                        }));

            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                gui.setDefaultClickAction(event -> event.setCancelled(true));
                gui.open(player);
            });

        });
    }
}
