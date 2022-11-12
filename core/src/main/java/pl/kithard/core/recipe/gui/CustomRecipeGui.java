package pl.kithard.core.recipe.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomRecipeGui {

    public void openBoyFarmer(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.DIAMOND_BLOCK)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.BOY_FARMER.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent -> {

                    craft(
                            player,
                            Arrays.asList(new ItemStack(Material.OBSIDIAN,8), new ItemStack(Material.DIAMOND_BLOCK, 1)),
                            CustomRecipe.BOY_FARMER);

                }));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openSandFarmer(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.SAND)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.SAND)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.SAND)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.SAND)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.DIAMOND_BLOCK)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.SAND)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.SAND)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.SAND)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.SAND)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.SAND_FARMER.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent -> {

                    craft(
                            player,
                            Arrays.asList(new ItemStack(Material.SAND,8), new ItemStack(Material.DIAMOND_BLOCK, 1)),
                            CustomRecipe.SAND_FARMER
                    );

                }));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openAirFarmer(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.EMERALD_BLOCK)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.DIAMOND_BLOCK)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.EMERALD_BLOCK)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.AIR_FARMER.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent -> {

                    craft(
                            player,
                            Arrays.asList(new ItemStack(Material.EMERALD_BLOCK,2), new ItemStack(Material.COBBLESTONE, 6), new ItemStack(Material.DIAMOND_BLOCK, 1)),
                            CustomRecipe.AIR_FARMER
                    );

                }));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openStoneGenerator(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.DIAMOND_BLOCK)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.COBBLESTONE)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.STONE_GENERATOR.getItem())
                .asGuiItem());
        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent ->
                        craft(
                                player,
                                Arrays.asList(new ItemStack(Material.COBBLESTONE,8), new ItemStack(Material.DIAMOND_BLOCK, 1)),
                                CustomRecipe.STONE_GENERATOR
                )));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openCobbleX(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.COBBLESTONE)
                .amount(64)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.COBBLEX.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent ->
                        craft(
                                player,
                                Collections.singletonList(new ItemStack(Material.COBBLESTONE, 576)),
                                CustomRecipe.COBBLEX)));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openThrownTnt(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.GOLD_BOOTS)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.GOLD_BLOCK)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.ANTI_LEGS.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent ->
                        craft(
                                player,
                                Arrays.asList(new ItemStack(Material.GOLD_BLOCK, 8), new ItemStack(Material.GOLD_BOOTS)),
                                CustomRecipe.ANTI_LEGS)));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void openEnderChest(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lCraftingi"))
                .rows(6)
                .create();

        this.fill(gui, player);

        gui.setItem(2, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(2, 3, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(2, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(3, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(3, 3, ItemBuilder.from(Material.ENDER_PEARL)
                .asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(4, 2, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(4, 3, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());
        gui.setItem(4, 4, ItemBuilder.from(Material.OBSIDIAN)
                .asGuiItem());

        gui.setItem(3,6, ItemBuilder.from(CustomRecipe.ENDER_CHEST.getItem())
                .asGuiItem());

        gui.setItem(3,7, ItemBuilder.from(Material.WORKBENCH)
                .name(TextUtil.component("&a&lAuto Crafting"))
                .lore(TextUtil.component(Arrays.asList(
                        "",
                        " &7Kliknij tutaj aby &bscraftowac &7automatycznie!"
                )))
                .asGuiItem(inventoryClickEvent ->
                        craft(
                                player,
                                Arrays.asList(new ItemStack(Material.OBSIDIAN,8), new ItemStack(Material.ENDER_PEARL, 1)),
                                CustomRecipe.ENDER_CHEST
                        )));


        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

    public void fill(Gui gui, Player player) {
        gui.getFiller().fill(ItemBuilder.from(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(6,2, ItemBuilder.from(CustomRecipe.BOY_FARMER.getItem())
                .asGuiItem(inventoryClickEvent -> openBoyFarmer(player)));
        gui.setItem(6,3, ItemBuilder.from(CustomRecipe.SAND_FARMER.getItem())
                .asGuiItem(inventoryClickEvent -> openSandFarmer(player)));
        gui.setItem(6,4, ItemBuilder.from(CustomRecipe.AIR_FARMER.getItem())
                .asGuiItem(inventoryClickEvent -> openAirFarmer(player)));
        gui.setItem(6,5, ItemBuilder.from(CustomRecipe.STONE_GENERATOR.getItem()).
                asGuiItem(inventoryClickEvent -> openStoneGenerator(player)));
        gui.setItem(6,6, ItemBuilder.from(CustomRecipe.COBBLEX.getItem())
                .asGuiItem(inventoryClickEvent -> openCobbleX(player)));
        gui.setItem(6,7, ItemBuilder.from(CustomRecipe.ANTI_LEGS.getItem())
                .asGuiItem(inventoryClickEvent -> openThrownTnt(player)));
        gui.setItem(6,8, ItemBuilder.from(CustomRecipe.ENDER_CHEST.getItem())
                .asGuiItem(inventoryClickEvent -> openEnderChest(player)));

    }

    private void craft(Player player, List<ItemStack> items, CustomRecipe result) {
        if (!InventoryUtil.hasItems(player, items)) {
            return;
        }

        InventoryUtil.removeItems(player, items);
        InventoryUtil.addItem(player, result.getItem());
    }

}
