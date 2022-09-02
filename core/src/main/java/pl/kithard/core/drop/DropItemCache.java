package pl.kithard.core.drop;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.drop.special.SpecialDropItem;
import pl.kithard.core.drop.special.SpecialDropItemType;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.CorePlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DropItemCache {

    private final CorePlugin plugin;

    private final List<DropItem> dropItems = new ArrayList<>();
    private final List<SpecialDropItem> specialDropItems = new ArrayList<>();

    public DropItemCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public Collection<DropItem> getDropItems() {
        return dropItems;
    }

    public Collection<SpecialDropItem> getSpecialDropItems() {
        return specialDropItems;
    }

    public void init() {
        Configuration configuration = this.plugin.getDropItemConfiguration().getCustomConfig();
        this.dropItems.clear();
        for (String s : configuration.getConfigurationSection("stone-drops").getKeys(false)) {

            DropItem dropItem = new DropItem(
                    s,
                    ItemBuilder.from(Material.valueOf(configuration.getString("stone-drops." + s + ".type"))).build(),
                    configuration.getDouble("stone-drops." + s + ".chance"),
                    configuration.getInt("stone-drops." + s + ".exp"),
                    configuration.getBoolean("stone-drops." + s + ".fortune"),
                    configuration.getBoolean("stone-drops." + s + ".guild-item"));

            this.getDropItems().add(dropItem);
        }

        this.specialDropItems.clear();
        for (String s : configuration.getConfigurationSection("magic-chest-drops").getKeys(false)) {

            String[] split = configuration.getString("magic-chest-drops." + s + ".item.type").split(":");

            int itemId = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            byte data = Byte.parseByte(split[2]);

            SpecialDropItem specialDropItem = new SpecialDropItem(
                    s,
                    ItemBuilder.from(new ItemStack(itemId, amount,  data))
                            .name(TextUtil.component(configuration.getString("magic-chest-drops." + s + ".item.name")))
                            .lore(TextUtil.component(configuration.getStringList("magic-chest-drops." + s + ".item.lore")))
                            .glow(configuration.getBoolean("magic-chest-drops." + s + ".item.glow"))
                            .build(),
                    configuration.getDouble("magic-chest-drops." + s + ".chance"),
                    SpecialDropItemType.MAGIC_CHEST);

            List<String> enchantList = configuration.getStringList("magic-chest-drops." + s + ".item.enchantments");

            if (enchantList.size() > 0) {
                for (String enchant : enchantList) {

                    String[] enchantArray = enchant.split(":");
                    String enchantment = enchantArray[0];
                    int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                    specialDropItem.getItem().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                }
            }

            this.getSpecialDropItems().add(specialDropItem);
        }

        for (String s : configuration.getConfigurationSection("cobblex-drops").getKeys(false)) {

            String[] split = configuration.getString("cobblex-drops." + s + ".item.type").split(":");

            int itemId = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            byte data = Byte.parseByte(split[2]);

            SpecialDropItem specialDropItem = new SpecialDropItem(
                    s,
                    ItemBuilder.from(new ItemStack(itemId, amount,  data))
                            .name(TextUtil.component(configuration.getString("cobblex-drops." + s + ".item.name")))
                            .lore(TextUtil.component(configuration.getStringList("cobblex-drops." + s + ".item.lore")))
                            .glow(configuration.getBoolean("cobblex-drops." + s + ".item.glow"))
                            .build(),
                    configuration.getDouble("cobblex-drops." + s + ".chance"),
                    SpecialDropItemType.COBBLEX);

            List<String> enchantList = configuration.getStringList("cobblex-drops." + s + ".item.enchantments");

            if (enchantList.size() > 0) {
                for (String enchant : enchantList) {

                    String[] enchantArray = enchant.split(":");
                    String enchantment = enchantArray[0];
                    int enchantMultiplier = Integer.parseInt(enchantArray[1]);

                    specialDropItem.getItem().addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
                }
            }

            this.getSpecialDropItems().add(specialDropItem);
        }
    }

}
