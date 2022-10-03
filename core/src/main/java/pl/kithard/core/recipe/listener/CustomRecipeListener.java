package pl.kithard.core.recipe.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.concurrent.TimeUnit;

public class CustomRecipeListener implements Listener {

    private final CorePlugin plugin;

    public CustomRecipeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        ItemStack result = event.getRecipe().getResult();
        CraftingInventory inventory = event.getInventory();
        if (result.equals(CustomRecipe.COBBLEX.getItem())) {

            ItemStack cobbleStone = new ItemStack(Material.COBBLESTONE, 64);

            for (int i = 1; i <= 9; i++) {
                if (inventory.getItem(i) != null) {
                    ItemStack inventoryItem = inventory.getItem(i);

                    if (inventoryItem.getType() != cobbleStone.getType()) {
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                    if (inventoryItem.getAmount() < cobbleStone.getAmount()) {
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {

        ItemStack result = event.getRecipe().getResult();
        CraftingInventory inventory = event.getInventory();
        if (result.equals(CustomRecipe.COBBLEX.getItem())) {
            ItemStack cobbleStone = new ItemStack(Material.COBBLESTONE, 64);

            for (int i = 1; i <= 9; i++) {
                if (inventory.getItem(i) != null) {

                    ItemStack inventoryItem = inventory.getItem(i);
                    if (inventoryItem.getType() != cobbleStone.getType()) {
                        event.setCancelled(true);
                    }
                    if (inventoryItem.getAmount() < cobbleStone.getAmount()) {
                        event.setCancelled(true);
                    }
                }

            }
            inventory.setResult(new ItemStack(Material.AIR));
            if (!event.isCancelled()){
                for (int i = 1; i <= 9; i++) {
                    InventoryUtil.removeItem(inventory,i, 64);
                    inventory.setResult(CustomRecipe.COBBLEX.getItem());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlaceFarmers(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getItemMeta() == null || itemInHand.getItemMeta().getDisplayName() == null) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlock().getLocation());

        if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.BOY_FARMER.getItem().getItemMeta().getDisplayName())) {

            if (guild == null) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie możesz &4stawiac &cfarmerow &4poza terenem gidlii!");
                event.setCancelled(true);
                return;
            }

            else if (this.plugin.getGuildCache().isNotAllowed(player, event.getBlock().getLocation(), GuildPermission.BOY_FARMER_PLACEMENT)) {
                event.setCancelled(true);
                return;
            }

            event.getBlockPlaced().setType(Material.OBSIDIAN);
            for (int height = event.getBlockPlaced().getY() - 1; height > 0; --height) {
                Block block = event.getBlock().getWorld().getBlockAt(event.getBlockPlaced().getX(), height, event.getBlockPlaced().getZ());
                if (guild.getRegion().isInHeart(block.getLocation())) {
                    continue;
                }

                if (block.getType() == Material.BEDROCK) {
                    return;
                }

                if (block.getType() == Material.SEA_LANTERN) {
                    continue;
                }

                block.setType(Material.OBSIDIAN);
            }
        }

        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.SAND_FARMER.getItem().getItemMeta().getDisplayName())) {

            if (guild == null) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie możesz &4stawiac &cfarmerow &4poza terenem gidlii!");
                event.setCancelled(true);
                return;
            }

            else if (this.plugin.getGuildCache().isNotAllowed(player, event.getBlock().getLocation(), GuildPermission.SAND_FARMER_PLACEMENT)) {
                event.setCancelled(true);
                return;
            }

            event.getBlockPlaced().setType(Material.SAND);
            for (int height = event.getBlockPlaced().getY() - 1; height > 0; --height) {
                Block block = event.getBlock().getWorld().getBlockAt(event.getBlockPlaced().getX(), height, event.getBlockPlaced().getZ());
                if (guild.getRegion().isInHeart(block.getLocation())) {
                    continue;
                }

                if (block.getType() == Material.BEDROCK) {
                    return;
                }

                if (block.getType() == Material.SEA_LANTERN) {
                    continue;
                }

                block.setType(Material.SAND);
            }
        }

        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.AIR_FARMER.getItem().getItemMeta().getDisplayName())) {

            if (guild == null) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie możesz &4stawiac &cfarmerow &4poza terenem gidlii!");
                event.setCancelled(true);
                return;
            }

            else if (this.plugin.getGuildCache().isNotAllowed(player, event.getBlock().getLocation(), GuildPermission.AIR_FARMER_PLACEMENT)) {
                event.setCancelled(true);
                return;
            }

            event.getBlockPlaced().setType(Material.AIR);
            for (int height = event.getBlockPlaced().getY() - 1; height > 0; --height) {
                Block block = event.getBlock().getWorld().getBlockAt(event.getBlockPlaced().getX(), height, event.getBlockPlaced().getZ());
                if (guild.getRegion().isInHeart(block.getLocation())) {
                    continue;
                }

                if (block.getType() == Material.BEDROCK) {
                    return;
                }

                if (block.getType() == Material.SEA_LANTERN) {
                    continue;
                }

                block.setType(Material.AIR);
            }

        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            return;
        }

        if (itemInHand.getItemMeta() == null || itemInHand.getItemMeta().getDisplayName() == null) {
            return;
        }

        if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.VIP_VOUCHER.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp vip 21d");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");

        }

        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.SVIP_VOUCHER.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp svip 21d");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");

        }
        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.SPONSOR_VOUCHER.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp sponsor 21d");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");

        }
        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.REPAIR_VOUCHER.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set kithard.commands.repair");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");
        }
        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.TURBO_DROP_VOUCHER_30MIN.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "turbo " + player.getName() + " 30m");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");
        }
        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.TURBO_DROP_VOUCHER_60MIN.getItem().getItemMeta().getDisplayName())) {
            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "turbo " + player.getName() + " 1h");
            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie aktywowano voucher!");
        }
    }

    @EventHandler
    public void onEnderChestBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.ENDER_CHEST)) {

            if (event.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            event.getBlock().getLocation().getBlock().setType(Material.AIR);

            if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.ENDER_CHEST));
            }
        }
    }



}
