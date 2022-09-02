package pl.kithard.core.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ItemUtil {

    private ItemUtil() {}

    public static int getFortuneLevel(ItemStack item) {
        return item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
    }

    public static void calculateDurability(Player player) {
        ItemStack itemInMainHand = player.getItemInHand();

        if (itemInMainHand == null || itemInMainHand.getType() == Material.AIR) {
            return;
        }

        String tool = itemInMainHand.getType().name().toUpperCase(Locale.ROOT);

        if (!(tool.endsWith("_PICKAXE")
                || tool.endsWith("_AXE")
                || tool.endsWith("_SWORD")
                || tool.endsWith("_SHOVEL"))) return;

        if (itemInMainHand.getType().isBlock()) {
            return;
        }

        short currentDamage = itemInMainHand.getDurability();
        short maxDamage = itemInMainHand.getType().getMaxDurability();

        short newDamage = (short) (currentDamage + 1);

        if (newDamage >= maxDamage) {
            player.setItemInHand(new ItemStack(Material.AIR));
            player.updateInventory();
        } else {
            itemInMainHand.setDurability(newDamage);
        }
    }
}
