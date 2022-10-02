package pl.kithard.core.shop;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.shop.item.ShopItem;
import pl.kithard.core.shop.item.ShopItemType;
import pl.kithard.core.shop.item.ShopVillager;
import pl.kithard.core.shop.item.ShopVillagerItem;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopConfiguration extends OkaeriConfig {


    private List<ShopItem> items = new ArrayList<>();
    private List<ShopVillager> villagers = new ArrayList<>();

    public ShopConfiguration() {
        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Helm 4/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_HELMET)
                        .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Klata 4/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_CHESTPLATE)
                        .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Spodnie 4/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_LEGGINGS)
                        .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Buty 4/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_BOOTS)
                        .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Miecz 4/1",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_SWORD)
                        .enchantment(Enchantment.DAMAGE_ALL, 4)
                        .enchantment(Enchantment.FIRE_ASPECT, 1)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Miecz 3/2",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_SWORD)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .enchantment(Enchantment.KNOCKBACK, 2)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Luk 3/1",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.BOW)
                        .enchantment(Enchantment.ARROW_DAMAGE, 3)
                        .enchantment(Enchantment.ARROW_FIRE, 1)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Strzaly",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.ARROW)
                        .amount(32)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Kilof 5/3/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                        .enchantment(Enchantment.DIG_SPEED, 5)
                        .enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Kilof 6/3/3",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                        .enchantment(Enchantment.DIG_SPEED, 6)
                        .enchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                        .enchantment(Enchantment.DURABILITY, 3)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Kox",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Refile",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.GOLDEN_APPLE, 12))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Perly",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.ENDER_PEARL, 3))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Sniezki",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.SNOW_BALL, 16))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Bloki Emeraldow",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.EMERALD_BLOCK, 64))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Wiaderko wody",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.WATER_BUCKET)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Wiaderko lavy",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(new ItemStack(Material.LAVA_BUCKET, 1))
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "SandFarmery",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.SAND_FARMER.getItem().clone())
                        .amount(32)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Kopacze Fosy",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.AIR_FARMER.getItem().clone())
                        .amount(32)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "BoyFarmery",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.BOY_FARMER.getItem().clone())
                        .amount(32)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Stoniarki",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.STONE_GENERATOR.getItem().clone())
                        .amount(10)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Anty nogi",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.ANTI_LEGS.getItem().clone())
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Voucher SVIP edycja",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.SVIP_VOUCHER.getItem().clone())
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "Voucher SPONSOR edycja",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.SPONSOR_VOUCHER.getItem().clone())
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "TURBODROP 30min",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_30MIN.getItem().clone())
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.BUY,
                "TURBODROP 60min",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(CustomRecipe.TURBO_DROP_VOUCHER_60MIN.getItem().clone())
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Diamenty",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.DIAMOND)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Emeraldy",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.EMERALD)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Zloto",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.GOLD_INGOT)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Zelazo",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.IRON_INGOT)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Redstone",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.REDSTONE)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Wegiel",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.COAL)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Ksiazka",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.BOOK)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Jablko",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.APPLE)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Obsydian",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.OBSIDIAN)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Kula szlamu",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.SLIME_BALL)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "TNT",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.TNT)
                        .amount(64)
                        .asItemStack()
        ));

        this.items.add(new ShopItem(
                ShopItemType.SELL,
                "Perla kresu",
                1.99,
                new ArrayList<>(),
                ItemStackBuilder.of(Material.ENDER_PEARL)
                        .amount(3)
                        .asItemStack()
        ));

        this.villagers.add(
                new ShopVillager(
                        "Zbrojownia",
                        new ItemStack(Material.IRON_CHESTPLATE),
                        Arrays.asList(
                                new ShopVillagerItem(
                                        "Helm 4/3",
                                        16,
                                        ItemStackBuilder.of(Material.IRON_HELMET)
                                                .enchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                                                .enchantment(Enchantment.DURABILITY, 3)
                                                .asItemStack())))
        );
    }

    public List<ShopItem> getItems() {
        return items;
    }

    public List<ShopVillager> getVillagers() {
        return villagers;
    }
}
