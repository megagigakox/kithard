package pl.kithard.core.player.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;

public class PlayerItemConsumeListener implements Listener {

    private final CorePlugin plugin;

    public PlayerItemConsumeListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.GOLDEN_APPLE) {
            if (item.getDurability() == 0) {
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 2, true, true), true);
                player.addPotionEffect(
                        new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 1, true, true), true);
            } else {

                Bukkit.getScheduler().runTaskLater(plugin, () -> {

                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 0, true, true), true);
                    player.addPotionEffect(
                            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 120, 0, true, true), true);

                },1L);
            }
        }

    }

}
