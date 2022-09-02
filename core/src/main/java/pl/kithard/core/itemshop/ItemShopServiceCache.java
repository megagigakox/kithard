package pl.kithard.core.itemshop;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.*;

public class ItemShopServiceCache {

    private final CorePlugin plugin;
    private final Map<String, ItemShopService> services = new HashMap<>();

    public ItemShopServiceCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.services.clear();
        Configuration config = this.plugin.getItemShopServiceConfiguration().getCustomConfig();

        for (String name : config.getConfigurationSection("services").getKeys(false)) {
            ItemShopService itemShopService = new ItemShopService(name,
                    config.getStringList("services." + name + ".commands"),
                    config.getStringList("services." + name + ".messages"));

            this.services.put(name, itemShopService);

            if (config.getConfigurationSection("services." + name + ".items") != null) {
                for (String item : config.getConfigurationSection("services." + name + ".items").getKeys(false)) {

                    List<String> enchantList = config.getStringList("services." + name + ".items." + item + ".enchantments");
                    String[] split = config.getString("services." + name + ".items." + item + ".type").split(":");

                    ItemStack itemStack = ItemBuilder.from(new ItemStack(Material.getMaterial(split[0]), Integer.parseInt(split[1])))
                            .name(TextUtil.component(config.getString("services." + name + ".items." + item + ".name")))
                            .lore(TextUtil.component(config.getStringList("services." + name + ".items." + item + ".lore")))
                            .build();

                    if (enchantList.size() > 0) {
                        for (String enchant : enchantList) {

                            String[] enchantArray = enchant.split(":");
                            String enchantment = enchantArray[0];
                            int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                            itemStack.addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                        }
                    }

                    itemShopService.getItems().add(itemStack);
                }
            }
        }
    }

    public ItemShopService findByKey(String key) {
        return this.services.get(key);
    }

    public void execute(String playerName, ItemShopService service) {
        for (String s : service.getCommands()) {
            this.plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), s.replace("{PLAYER}", playerName));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

            if (!corePlayer.isDisabledSetting(PlayerSettings.ITEM_SHOP_SERVICES_MESSAGES)) {
                for (String s : service.getMessages()) {
                    TextUtil.message(player, s.replace("{PLAYER}", playerName));
                }
            }
        }

        if (service.getItems() != null) {
            Player player = this.plugin.getServer().getPlayerExact(playerName);
            if (player == null) {
                return;
            }

            for (ItemStack is : service.getItems()) {
                player.getInventory().addItem(is);
            }
        }
    }

    public Map<String, ItemShopService> getServices() {
        return services;
    }
}
