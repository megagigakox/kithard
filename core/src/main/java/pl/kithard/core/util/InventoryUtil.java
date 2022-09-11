package pl.kithard.core.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public final class InventoryUtil {

    private InventoryUtil() {}

    public static boolean hasItems(Player player, List<ItemStack> items) {
        for (ItemStack item : items) {
            if (!hasItem(player, item)) return false;
        }

        return true;
    }

    public static int countItemsIgnoreItemMeta(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return 0;

        PlayerInventory inventory = player.getInventory();
        int count = 0;
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);

            if (itemStack == null || !isSimilarExceptItemMeta(item, itemStack)) continue;

            count += itemStack.getAmount();
        }

        return count;
    }

    private static boolean isSimilarExceptItemMeta(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null) return false;

        return stack1.getType() == stack2.getType() && stack1.getDurability() == stack2.getDurability();
    }

    public static int countAmountForDeposit(Player player, ItemStack item) {
        int amount = 0;
        PlayerInventory playerInventory = player.getInventory();

        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack is = playerInventory.getItem(i);

            if (is == null) continue;

            if (is.getType() == item.getType()
                    && is.getDurability() == item.getDurability()
                    && is.getData().equals(item.getData())) {

                amount += is.getAmount();
            }
        }
        return amount;
    }


    public static boolean hasItem(Player player, ItemStack item, int amount) {
        if (item == null || item.getType() == Material.AIR) return false;

        return player.getInventory().containsAtLeast(item, amount);
    }

    public static boolean hasItem(Player player, Material material, int amount) {
        return hasItem(player, new ItemStack(material, amount));
    }

    public static boolean hasItem(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return true;

        return hasItem(player, item, item.getAmount());
    }

    public static void addItem(Player player, ItemStack item) {
        if (item == null) return;

        Map<Integer, ItemStack> leftOver = player.getInventory().addItem(item.clone());

        for (ItemStack leftoverItem : leftOver.values()) {
            player.getWorld().dropItem(player.getLocation(), leftoverItem.clone());
        }
    }

    public static void addItems(Player player, List<ItemStack> items, Block block) {

        Map<Integer, ItemStack> leftOver = player.getInventory().addItem(items.toArray(new ItemStack[0]));

        for (Map.Entry<Integer, ItemStack> en : leftOver.entrySet()) {
            block.getWorld().dropItemNaturally(block.getLocation(), en.getValue());
        }
    }

    public static void addItem(Player player, List<ItemStack> items) {
        Map<Integer, ItemStack> leftOver =
                player
                        .getInventory()
                        .addItem(
                                items.stream()
                                        .filter(Objects::nonNull)
                                        .map(ItemStack::clone)
                                        .toArray(ItemStack[]::new));

        for (ItemStack leftoverItem : leftOver.values()) {
            player.getWorld().dropItem(player.getLocation(), leftoverItem.clone());
        }
    }

    public static void removeItemIgnoreItemMeta(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;

        int amountLeft = item.getAmount();
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack itemStack = contents[i];
            if (itemStack != null
                    && itemStack.getType() == item.getType()
                    && itemStack.getDurability() == item.getDurability()) {
                if (amountLeft >= itemStack.getAmount()) {
                    amountLeft -= itemStack.getAmount();

                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                } else {
                    itemStack.setAmount(itemStack.getAmount() - amountLeft);
                }

                if (amountLeft == 0) return;
            }
        }
    }

    public static void removeItemByDisplayName(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;

        int amountLeft = item.getAmount();
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack itemStack = contents[i];
            if (itemStack != null
                    && itemStack.getType() == item.getType()
                    && itemStack.getDurability() == item.getDurability()
                    && TextUtil.color(itemStack.getItemMeta().getDisplayName()).equalsIgnoreCase(TextUtil.color(item.getItemMeta().getDisplayName()))) {
                if (amountLeft >= itemStack.getAmount()) {
                    amountLeft -= itemStack.getAmount();

                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                } else {
                    itemStack.setAmount(itemStack.getAmount() - amountLeft);
                }

                if (amountLeft == 0) return;
            }
        }
    }

    public static void remove(ItemStack is, Player player, int amount) {
        int removed = 0;
        boolean all = false;
        final List<ItemStack> toRemove = new ArrayList<>();
        final ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack item : contents) {
            if (item != null && !item.getType().equals(Material.AIR) && item.getType().equals(is.getType()) && item.getDurability() == is.getDurability() && !all && removed != amount) {
                if (item.getAmount() == amount) {
                    if (removed == 0) {
                        toRemove.add(item.clone());
                        all = true;
                        removed = item.getAmount();
                    } else {
                        int a = amount - removed;
                        ItemStack s = item.clone();
                        s.setAmount(a);
                        toRemove.add(s);
                        removed += a;
                        all = true;
                    }
                } else if (item.getAmount() > amount) {
                    if (removed == 0) {
                        ItemStack s2 = item.clone();
                        s2.setAmount(amount);
                        toRemove.add(s2);
                        all = true;
                        removed = amount;
                    } else {
                        int a = amount - removed;
                        ItemStack s = item.clone();
                        s.setAmount(a);
                        toRemove.add(s);
                        removed += a;
                        all = true;
                    }
                } else if (item.getAmount() < amount) {
                    if (removed == 0) {
                        toRemove.add(item.clone());
                        removed = item.getAmount();
                    } else {
                        int a = amount - removed;
                        if (a == item.getAmount()) {
                            toRemove.add(item.clone());
                            removed += item.getAmount();
                            all = true;
                        } else if (item.getAmount() > a) {
                            ItemStack s = item.clone();
                            s.setAmount(a);
                            toRemove.add(s);
                            removed += a;
                            all = true;
                        } else if (item.getAmount() < a) {
                            toRemove.add(item.clone());
                            removed += item.getAmount();
                        }
                    }
                }
            }
        }
        removeItem(player, toRemove);
    }
    public static void removeItem(Player player, List<ItemStack> items) {
        if (player == null || items == null || items.isEmpty()) {
            return;
        }
        for (ItemStack is : items) {
            player.getInventory().removeItem(is);
        }
    }

    public static void removeItem(Player player, Material material, int amount, short durability) {
        removeItem(player, new ItemStack(material, amount, durability));
    }

    public static void removeItem(Player player, Material material, int amount) {
        removeItem(player, new ItemStack(material, amount));
    }

    public static void removeItem(Player player, ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return;

        player.getInventory().removeItem(item);
    }

    public static void removeItems(Player player, List<ItemStack> items) {
        player.getInventory().removeItem(items.stream().filter(Objects::nonNull).toArray(ItemStack[]::new));
    }

    public static void removeItem(Inventory inventory, int slot, int amount) {
        if (inventory.getItem(slot) == null) {
            return;
        }

        ItemStack item = inventory.getItem(slot).clone();
        if (item == null || item.getType().equals(Material.AIR)) {
            return;
        }
        if (item.getAmount() <= amount) {
            inventory.setItem(slot, new ItemStack(Material.AIR));
            return;
        }
        item.setAmount(item.getAmount() - amount);
        inventory.setItem(slot, item);
    }

    public static void removeItems(Inventory inv, ItemStack item, int amount) {
        int toRemove = amount;

        Map<Integer, ItemStack> slots = new HashMap<>();
        ItemStack slotItem;
        for (int slot = 0; slot < inv.getSize(); slot++) {
            slotItem = inv.getItem(slot);
            if ((slotItem != null) && (slotItem.getType() != Material.AIR) && (slotItem.getType() == item.getType())
                    && (slotItem.getDurability() == item.getDurability())) {
                slots.put(slot, slotItem);
            }
        }
        for (Map.Entry<Integer, ItemStack> entrySlots : slots.entrySet()) {
            if (entrySlots.getValue().getAmount() <= toRemove) {
                inv.setItem(entrySlots.getKey(), new ItemStack(Material.AIR));
                toRemove -= entrySlots.getValue().getAmount();
            } else {
                ItemStack invItem = inv.getItem(entrySlots.getKey());
                invItem.setAmount(invItem.getAmount() - toRemove);
                break;
            }
        }
    }
}
