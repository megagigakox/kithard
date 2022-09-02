package pl.kithard.core.util;

import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class ItemStackBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private ItemStackBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    private ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public static ItemStackBuilder of(Material material) {
        return new ItemStackBuilder(material, 1);
    }

    public static ItemStackBuilder of(Material material, int amount) {
        return new ItemStackBuilder(material, amount);
    }

    public static ItemStackBuilder of(ItemStack item) {
        return new ItemStackBuilder(item);
    }

    public void refreshMeta() {
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStackBuilder name(String name) {
        this.itemMeta.setDisplayName(TextUtil.color(name));
        this.refreshMeta();

        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        this.itemMeta.setLore(TextUtil.color(lore));
        this.refreshMeta();

        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemStackBuilder appendLore(List<String> lore) {
        ItemMeta itemMeta = this.itemMeta;
        if (!itemMeta.hasLore()) {
            itemMeta.setLore(TextUtil.color(lore));
        } else {
            List<String> newLore = itemMeta.getLore();
            newLore.addAll(TextUtil.color(lore));
            itemMeta.setLore(newLore);
        }

        refreshMeta();
        return this;
    }

    public ItemStackBuilder appendLore(String lore) {
        return this.appendLore(Collections.singletonList(lore));
    }

    public ItemStackBuilder appendLore(String... lore) {
        return this.appendLore(Arrays.asList(lore));
    }

    public ItemStackBuilder enchantment(Enchantment enchant, int level) {
        this.itemMeta.addEnchant(enchant, level, true);
        this.refreshMeta();

        return this;
    }

    public ItemStackBuilder flag(ItemFlag flag) {
        this.itemMeta.addItemFlags(flag);
        this.refreshMeta();

        return this;
    }

    public ItemStackBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder glow() {
        return this.glow(true);
    }

    public ItemStackBuilder glow(boolean glow) {
        if (glow) {
            this.itemMeta.addEnchant(Enchantment.LURE, 1, false);
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {

            for (Enchantment enchantment : this.itemMeta.getEnchants().keySet()) {
                this.itemMeta.removeEnchant(enchantment);
            }

        }
        refreshMeta();
        return this;
    }

    public ItemMeta getMeta() {
        return this.itemMeta;
    }

    public ItemStack asItemStack() {
        return this.itemStack;
    }

    public GuiItem asGuiItem() {
        return new GuiItem(this.itemStack);
    }

    public GuiItem asGuiItem(GuiAction<InventoryClickEvent> event) {
        return new GuiItem(this.itemStack, event);
    }
}