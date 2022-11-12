package pl.kithard.core.safe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

public class SafeListener implements Listener {

    private final CorePlugin plugin;

    public SafeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }

        if (item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.CROWBAR.getItem().getItemMeta().getDisplayName())) {
                openCrowbar(player);
                event.setCancelled(true);
                return;
            }

            Safe safe = this.plugin.getSafeCache().findByName(item.getItemMeta().getDisplayName());
            if (safe == null) {
                return;
            }

            if (!safe.getOwnerUUID().equals(player.getUniqueId()) && !player.hasPermission("kithard.safe.bypass")) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz otworzyc tego sejfu poniewaz nie nalezy on do Ciebie! Jezeli chcesz przejac ten sejf musisz posiadać &b&lŁOM&c!");
                event.setCancelled(true);
                return;
            }

            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            if (corePlayer.getCombat().hasFight()) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz otworzyc sejfu podczas walki!");
                event.setCancelled(true);
                return;
            }

            safe.open(player);
            player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void cancelSafe(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (player.getOpenInventory().getTopInventory().getName().contains("SEJF") && (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || event.getAction() == InventoryAction.HOTBAR_SWAP)) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return;
        }

        if (inventory.getName().contains("SEJF")) {

            if (item.getItemMeta().getDisplayName().contains("SEJF")) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }

    }

    @EventHandler(ignoreCancelled = true)
    public void crowbar(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) {
            return;
        }

        Inventory inventory = event.getClickedInventory();
        if (inventory.getName().equalsIgnoreCase(TextUtil.color("&3&lŁOM"))) {

            if (event.getRawSlot() == 13) {
                Safe safe = this.plugin.getSafeCache().findByName(item.getItemMeta().getDisplayName());
                if (safe != null && !safe.getOwnerName().equalsIgnoreCase(player.getName())) {
                    inventory.setItem(13, new ItemStack(Material.AIR));
                    safe.setOwnerUUID(player.getUniqueId());
                    safe.setOwnerName(player.getName());
                    safe.setNeedSave(true);
                    ItemStack itemStack = safe.item();
                    InventoryUtil.addItem(player, itemStack);
                    InventoryUtil.removeItem(player, CustomRecipe.CROWBAR.getItem().clone());
                    player.closeInventory();
                    TextUtil.message(player, "&8(&3&l!&8) &7Od teraz ten sejf nalezy do ciebie!");
                }
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Safe safe = this.plugin.getSafeCache().findByName(event.getInventory().getName());
        if (safe == null) {
            return;
        }

        if (event.getInventory().getContents() != null) {
            ItemStack[] current = event.getInventory().getContents();
            ItemStack[] last = safe.getContents();
            if (!Arrays.equals(current, last)) {
                safe.setContents(event.getInventory().getContents());
                safe.setNeedSave(true);
            }

        }
    }

    @EventHandler
    public void onThrow(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemDrop().getItemStack();
        if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName() == null) {
            return;
        }

        if (itemStack.getItemMeta().getDisplayName().contains("SEJF")) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz wyrzucic sejfu, aby przekazac innemu graczowi uzyj bezpieczna wymiane na spawnie!");
            event.setCancelled(true);
        }
    }

    public void openCrowbar(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, TextUtil.color("&3&lŁOM"));
        for (int i = 0; i < inventory.getSize(); ++i) {
            inventory.setItem(i, GuiHelper.BLACK_STAINED_GLASS_PANE);
        }

        inventory.setItem(13, new ItemStack(Material.AIR));
        player.openInventory(inventory);
    }
}
