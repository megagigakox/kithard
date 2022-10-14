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
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.player.CorePlayer;

import java.util.concurrent.TimeUnit;

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
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

            if (item.getDurability() == 0) {
                corePlayer.addAchievementProgress(AchievementType.EATEN_GOLDEN_APPLES, 1);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    player.removePotionEffect(PotionEffectType.REGENERATION);
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 3));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 1));
                }, 1L);

            } else if (item.getDurability() == 1) {
                corePlayer.addAchievementProgress(AchievementType.EATEN_ENCHANTED_GOLDEN_APPLES, 1);

                Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                    player.removePotionEffect(PotionEffectType.REGENERATION);
                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    player.removePotionEffect(PotionEffectType.ABSORPTION);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 4));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 120, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 120, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 120, 0));
                }, 1L);
            }
        }

    }

}
