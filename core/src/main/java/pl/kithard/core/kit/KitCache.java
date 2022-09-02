package pl.kithard.core.kit;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.CorePlugin;

import java.util.ArrayList;
import java.util.List;

public class KitCache {

    private final CorePlugin plugin;
    private final List<Kit> kits = new ArrayList<>();

    public KitCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public void init() {
        kits.clear();
        Configuration configuration = this.plugin.getKitConfiguration().getCustomConfig();

        for (String kit : configuration.getConfigurationSection("kits").getKeys(false)) {
            String[] splitIcon = configuration.getString("kits." + kit + ".item").split(":");

            int itemIdIcon = Integer.parseInt(splitIcon[0]);
            int amountIcon = Integer.parseInt(splitIcon[1]);
            byte dataIcon = Byte.parseByte(splitIcon[2]);

            String cooldown = configuration.getString("kits." + kit + ".cooldown");
            Kit a = new Kit(configuration.getString("kits." + kit + ".name"), configuration.getString("kits." + kit + ".guiName"),
                    configuration.getInt("kits." + kit + ".guiSlot"),
                    configuration.getString("kits." + kit + ".permission"),
                    cooldown, new ItemStack(itemIdIcon, amountIcon, dataIcon));

            for (String s : configuration.getStringList("kits." + kit + ".lore")) {
                a.getLore().add(s);
            }

            this.kits.add(a);

            for (String item : configuration.getConfigurationSection("kits." + kit + ".items").getKeys(false)) {

                String[] split = configuration.getString("kits." + kit + ".items." + item + ".type").split(":");

                int itemId = Integer.parseInt(split[0]);
                int amount = Integer.parseInt(split[1]);
                byte data = Byte.parseByte(split[2]);

                ItemStack kitItem = ItemBuilder.from(new ItemStack(itemId, amount, data))
                        .name(TextUtil.component(configuration.getString("kits." + kit + ".items." + item + ".name")))
                        .lore(TextUtil.component(configuration.getStringList("kits." + kit + ".items." + item + ".lore")))
                        .glow(configuration.getBoolean("kits." + kit + ".items." + item + ".glow"))
                        .build();

                List<String> enchantList = configuration.getStringList("kits." + kit + ".items." + item + ".enchantments");

                if (enchantList.size() > 0) {
                    for (String enchant : enchantList) {


                        String[] enchantArray = enchant.split(":");
                        String enchantment = enchantArray[0];
                        int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                        kitItem.addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                    }
                }

                a.getItems().add(kitItem);

            }
        }
    }
}