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


    private final List<ShopItem> items = new ArrayList<>();
    private final List<ShopVillager> villagers = new ArrayList<>();

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


//    public void init() {
//
//        this.buyItems.clear();;
//        this.sellItems.clear();
//        this.villagers.clear();
//
//        Configuration configuration = this.plugin.getShopConfiguration().getCustomConfig();
//
//        for (String id : configuration.getConfigurationSection("buy-items").getKeys(false)) {
//
//            String[] split = configuration.getString("buy-items." + id + ".item").split(":");
//
//            int itemId = Integer.parseInt(split[0]);
//            int amount = Integer.parseInt(split[1]);
//            byte data = Byte.parseByte(split[2]);
//
//            ShopItem shopBuyItem = new ShopItem(configuration.getString("buy-items." + id + ".name"),
//                    configuration.getDouble("buy-items." + id + ".price"),
//                    configuration.getStringList("buy-items." + id + ".afterBuy.commands"),
//                    ItemBuilder.from(new ItemStack(itemId, amount,  data)).build(),
//                    ItemBuilder.from(new ItemStack(itemId, amount,  data))
//                            .name(TextUtil.component(configuration.getString("buy-items." + id + ".afterBuy.name")))
//                            .lore(TextUtil.component(configuration.getStringList("buy-items." + id + ".afterBuy.lore")))
//                            .build());
//
//            List<String> enchantList = configuration.getStringList("buy-items." + id + ".enchantments");
//
//            if (enchantList.size() > 0) {
//                for (String enchant : enchantList) {
//
//                    String[] enchantArray = enchant.split(":");
//                    String enchantment = enchantArray[0];
//                    int enchantMultiplier = Integer.parseInt(enchantArray[1]);
//
//                    shopBuyItem.getInGui().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
//                    shopBuyItem.getAfterBuy().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
//                }
//            }
//
//            this.buyItems.add(shopBuyItem);
//        }
//
//        for (String id : configuration.getConfigurationSection("sell-items").getKeys(false)) {
//
//            String[] split = configuration.getString("sell-items." + id + ".item").split(":");
//
//            int itemId = Integer.parseInt(split[0]);
//            int amount = Integer.parseInt(split[1]);
//            byte data = Byte.parseByte(split[2]);
//
//            ShopSellItem b = new ShopSellItem(
//                    configuration.getString("sell-items." + id + ".name"),
//                    configuration.getDouble("sell-items." + id + ".price"),
//                    ItemBuilder.from(new ItemStack(itemId, amount,  data)).build());
//
//            this.sellItems.add(b);
//        }
//
//        for (String id : configuration.getConfigurationSection("villager-items").getKeys(false)) {
//            ShopVillager b = new ShopVillager(
//                    configuration.getString("villager-items." + id + ".name"),
//                    ItemBuilder.from(Material.valueOf(configuration.getString("villager-items." + id + ".material"))).build());
//
//            this.villagers.add(b);
//
//            for (String item : configuration.getConfigurationSection("villager-items." + id + ".items").getKeys(false)) {
//
//                String[] split = configuration.getString("villager-items." + id + ".items." + item + ".item").split(":");
//
//                int itemId = Integer.parseInt(split[0]);
//                int amount = Integer.parseInt(split[1]);
//                byte data = Byte.parseByte(split[2]);
//
//                ShopVillagerItem shopVillagerItem = new ShopVillagerItem(
//                        configuration.getString("villager-items." + id + ".items." + item + ".name"),
//                        configuration.getInt("villager-items." + id + ".items." + item + ".price"),
//                        ItemBuilder.from(new ItemStack(itemId, amount,  data)).build(),
//                        ItemBuilder.from(new ItemStack(itemId, amount,  data))
//                                .name(TextUtil.component(configuration.getString("villager-items." + id + ".items." + item + ".afterBuy.name")))
//                                .lore(TextUtil.component(configuration.getStringList("villager-items." + id + ".items." + item + ".afterBuy.lore")))
//                                .build());
//
//                List<String> enchantList = configuration.getStringList("villager-items." + id + ".items." + item + ".enchantments");
//
//                if (enchantList.size() > 0) {
//                    for (String enchant : enchantList) {
//
//                        String[] enchantArray = enchant.split(":");
//                        String enchantment = enchantArray[0];
//                        int enchantMultiplier = Integer.parseInt(enchantArray[1]);
//
//                        shopVillagerItem.getInGui().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
//                        shopVillagerItem.getAfterBuy().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
//                    }
//                }
//
//                b.getItems().add(shopVillagerItem);
//            }
//        }
//
//    }

    public List<ShopItem> getItems() {
        return items;
    }

    public List<ShopVillager> getVillagers() {
        return villagers;
    }
}
