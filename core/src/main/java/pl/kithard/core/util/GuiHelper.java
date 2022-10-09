package pl.kithard.core.util;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.builder.item.SkullBuilder;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class GuiHelper {

    private GuiHelper() {}

    public final static ItemStack BLACK_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack GRAY_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack LIGHT_BLUE_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack BLUE_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack WHITE_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack CYAN_STAINED_GLASS_PANE = ItemBuilder.from(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9))
            .name(TextUtil.component(" "))
            .build();

    public final static ItemStack BACK_ITEM = ItemStackBuilder.of(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmM2OGEzNDJkZjZjOWU3MTFlYzNiYzEzOGY5MWNkYjFiMGZhNWU3MmY2NmU0MjUxODc1ZDhiZWRkMDU1M2ViNSJ9fX0="))
            .name("&cPowrot")
            .asItemStack();

    public final static ItemStack NEXT_ITEM =
            ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY3MWM0YzA0MzM3YzM4YTVjN2YzMWE1Yzc1MWY5OTFlOTZjMDNkZjczMGNkYmVlOTkzMjA2NTVjMTlkIn19fQ==")
                    .name(TextUtil.component("&aNastepna strona"))
                    .build();

    public final static ItemStack PREVIOUS_ITEM =
            ItemBuilder.skull().texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTM5NzExMjRiZTg5YWM3ZGM5YzkyOWZlOWI2ZWZhN2EwN2NlMzdjZTFkYTJkZjY5MWJmODY2MzQ2NzQ3N2M3In19fQ==")
                    .name(TextUtil.component("&cPoprzednia strona"))
                    .build();

    public static void fillColorGui3(BaseGui gui) {
        gui.getFiller().fillBorder(ItemBuilder.from(CYAN_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(1,1, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(1,5, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(1,9, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(3,1, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(3,2, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(3,3, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(3,7, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(3,8, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(3,9, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
    }

    public static void fillColorGui6(BaseGui gui) {
        gui.getFiller().fillBorder(ItemBuilder.from(CYAN_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(1, 2, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(1, 5, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(1, 8, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(2, 1, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(2, 9, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(5, 1, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(5, 9, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(6, 2, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(6, 8, ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem());

    }

    public static void fillColorGui5(BaseGui gui) {
        GuiItem blue = ItemBuilder.from(CYAN_STAINED_GLASS_PANE)
                .asGuiItem();

        GuiItem lightBlue = ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE)
                .asGuiItem();

        gui.getFiller().fillBorder(blue);

        gui.setItem(1, 3, lightBlue);
        gui.setItem(1, 4, lightBlue);
        gui.setItem(1, 5, lightBlue);
        gui.setItem(1, 6, lightBlue);
        gui.setItem(1, 7, lightBlue);
        gui.setItem(4, 1, lightBlue);
        gui.setItem(4, 9, lightBlue);
        gui.setItem(5, 1, lightBlue);
        gui.setItem(5, 9, lightBlue);
        gui.setItem(5, 4, lightBlue);
        gui.setItem(5, 5, lightBlue);
        gui.setItem(5, 6, lightBlue);

    }

    public static void fillColorMain(BaseGui gui) {
        gui.setItem(
                Arrays.asList(0, 1, 7, 8, 9, 12, 13, 14, 17, 27, 30, 31, 32, 35, 36, 37, 43, 44),
                ItemBuilder.from(GRAY_STAINED_GLASS_PANE).asGuiItem()
        );

        gui.setItem(
                Arrays.asList(2, 4, 6, 19, 25, 38, 40, 42),
                ItemBuilder.from(CYAN_STAINED_GLASS_PANE).asGuiItem()
        );

        gui.setItem(
                Arrays.asList(3, 5, 10, 11, 15, 16, 18, 26, 28, 29, 33, 34, 39, 41),
                ItemBuilder.from(LIGHT_BLUE_STAINED_GLASS_PANE).asGuiItem()
        );
    }


}
