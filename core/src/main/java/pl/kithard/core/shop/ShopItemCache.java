package pl.kithard.core.shop;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.shop.item.ShopBuyItem;
import pl.kithard.core.shop.item.ShopSellItem;
import pl.kithard.core.shop.item.ShopVillagerItem;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.CorePlugin;

import java.util.ArrayList;
import java.util.List;

public class ShopItemCache {

    private final CorePlugin plugin;

    private final List<ShopBuyItem> buyItems = new ArrayList<>();
    private final List<ShopSellItem> sellItems = new ArrayList<>();
    private final List<ShopVillager> villagers = new ArrayList<>();

    public ShopItemCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        this.buyItems.clear();;
        this.sellItems.clear();
        this.villagers.clear();

        Configuration configuration = this.plugin.getShopConfiguration().getCustomConfig();

        for (String id : configuration.getConfigurationSection("buy-items").getKeys(false)) {

            String[] split = configuration.getString("buy-items." + id + ".item").split(":");

            int itemId = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            byte data = Byte.parseByte(split[2]);

            ShopBuyItem shopBuyItem = new ShopBuyItem(configuration.getString("buy-items." + id + ".name"),
                    configuration.getDouble("buy-items." + id + ".price"),
                    configuration.getStringList("buy-items." + id + ".afterBuy.commands"),
                    ItemBuilder.from(new ItemStack(itemId, amount,  data)).build(),
                    ItemBuilder.from(new ItemStack(itemId, amount,  data))
                            .name(TextUtil.component(configuration.getString("buy-items." + id + ".afterBuy.name")))
                            .lore(TextUtil.component(configuration.getStringList("buy-items." + id + ".afterBuy.lore")))
                            .build());

            List<String> enchantList = configuration.getStringList("buy-items." + id + ".enchantments");

            if (enchantList.size() > 0) {
                for (String enchant : enchantList) {

                    String[] enchantArray = enchant.split(":");
                    String enchantment = enchantArray[0];
                    int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                    shopBuyItem.getInGui().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                    shopBuyItem.getAfterBuy().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                }
            }

            this.buyItems.add(shopBuyItem);
        }

        for (String id : configuration.getConfigurationSection("sell-items").getKeys(false)) {

            String[] split = configuration.getString("sell-items." + id + ".item").split(":");

            int itemId = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            byte data = Byte.parseByte(split[2]);

            ShopSellItem b = new ShopSellItem(
                    configuration.getString("sell-items." + id + ".name"),
                    configuration.getDouble("sell-items." + id + ".price"),
                    ItemBuilder.from(new ItemStack(itemId, amount,  data)).build());

            this.sellItems.add(b);
        }

        for (String id : configuration.getConfigurationSection("villager-items").getKeys(false)) {
            ShopVillager b = new ShopVillager(
                    configuration.getString("villager-items." + id + ".name"),
                    ItemBuilder.from(Material.valueOf(configuration.getString("villager-items." + id + ".material"))).build());

            this.villagers.add(b);

            for (String item : configuration.getConfigurationSection("villager-items." + id + ".items").getKeys(false)) {

                String[] split = configuration.getString("villager-items." + id + ".items." + item + ".item").split(":");

                int itemId = Integer.parseInt(split[0]);
                int amount = Integer.parseInt(split[1]);
                byte data = Byte.parseByte(split[2]);

                ShopVillagerItem shopVillagerItem = new ShopVillagerItem(
                        configuration.getString("villager-items." + id + ".items." + item + ".name"),
                        configuration.getInt("villager-items." + id + ".items." + item + ".price"),
                        ItemBuilder.from(new ItemStack(itemId, amount,  data)).build(),
                        ItemBuilder.from(new ItemStack(itemId, amount,  data))
                                .name(TextUtil.component(configuration.getString("villager-items." + id + ".items." + item + ".afterBuy.name")))
                                .lore(TextUtil.component(configuration.getStringList("villager-items." + id + ".items." + item + ".afterBuy.lore")))
                                .build());

                List<String> enchantList = configuration.getStringList("villager-items." + id + ".items." + item + ".enchantments");

                if (enchantList.size() > 0) {
                    for (String enchant : enchantList) {

                        String[] enchantArray = enchant.split(":");
                        String enchantment = enchantArray[0];
                        int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                        shopVillagerItem.getInGui().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                        shopVillagerItem.getAfterBuy().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                    }
                }

                b.getItems().add(shopVillagerItem);
            }
        }

    }

    public List<ShopBuyItem> getBuyItems() {
        return buyItems;
    }

    public List<ShopSellItem> getSellItems() {
        return sellItems;
    }

    public List<ShopVillager> getVillagers() {
        return villagers;
    }
}
