package pl.kithard.core.settings.listener;

import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.combat.listener.PlayerDamageListener;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.TextUtil;

public class ServerSettingsListeners implements Listener {

    private final CorePlugin plugin;

    public ServerSettingsListeners(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.LEVER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getRecipe().getResult();
        if (result.getType() == Material.FISHING_ROD && !this.plugin.getServerSettings().isEnabled(ServerSettingsType.FISHING_RODS)) {
            event.setCancelled(true);
            return;
        }

        if (result.getType() == Material.DIAMOND_BOOTS || result.getType() == Material.DIAMOND_LEGGINGS
                || result.getType() == Material.DIAMOND_CHESTPLATE || result.getType() == Material.DIAMOND_HELMET
                || result.getType() == Material.DIAMOND_SWORD || result.getType() == Material.DIAMOND_AXE) {

            if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.DIAMOND_ITEMS)) {
                TextUtil.message(event.getWhoClicked(), "&8(&4&l!&8) &cCraftowanie diamentowych itemow zostalo tymczasowo wylaczone!");
                event.setCancelled(true);

            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() == Material.BEACON && !this.plugin.getServerSettings().isEnabled(ServerSettingsType.BEACON)) {
            TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cStawianie beaconow zostalo tymczasowo wylaczone!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onAnvilUse(InventoryClickEvent event) {
        if (event.getView().getType() == InventoryType.ANVIL) {

            int rawSlot = event.getRawSlot();
            if (rawSlot == 2) {
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack == null) {
                    return;
                }

                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) {
                    return;
                }

                if (itemStack.getType() == Material.IRON_BOOTS) {
                    if (itemMeta.getEnchants().get(Enchantment.PROTECTION_FALL) != null && itemMeta.getEnchants().get(Enchantment.PROTECTION_FALL) > 2) {
                        itemMeta.removeEnchant(Enchantment.PROTECTION_FALL);
                        itemMeta.addEnchant(Enchantment.PROTECTION_FALL, 2, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    return;
                }

                if (itemStack.getType().toString().contains("SWORD")) {
                    if (itemMeta.getEnchants().get(Enchantment.DAMAGE_ALL) != null && itemMeta.getEnchants().get(Enchantment.DAMAGE_ALL) > 3) {
                        itemMeta.removeEnchant(Enchantment.DAMAGE_ALL);
                        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    if (itemMeta.getEnchants().get(Enchantment.FIRE_ASPECT) != null && itemMeta.getEnchants().get(Enchantment.FIRE_ASPECT) > 1) {
                        itemMeta.removeEnchant(Enchantment.FIRE_ASPECT);
                        itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    return;
                }

                if (itemStack.getType() == Material.BOW) {
                    if (itemMeta.getEnchants().get(Enchantment.ARROW_DAMAGE) != null && itemMeta.getEnchants().get(Enchantment.ARROW_DAMAGE) > 3) {
                        itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
                        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                    if (itemMeta.getEnchants().get(Enchantment.ARROW_FIRE) != null && itemMeta.getEnchants().get(Enchantment.ARROW_FIRE) > 1) {
                        itemMeta.removeEnchant(Enchantment.ARROW_FIRE);
                        itemMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                        itemStack.setItemMeta(itemMeta);
                    }
                }

            }

        }
    }


}
