package pl.kithard.core.guild.task;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class GuildShadowBlockProtectionTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public GuildShadowBlockProtectionTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0L, 20 * 5L);
    }

    @Override
    public void run() {
        for (Player player : this.plugin.getServer().getOnlinePlayers()) {

            Guild guild = this.plugin.getGuildCache().findByLocation(player.getLocation());
            if (guild == null) {
                continue;
            }

            if (guild.isMember(player.getUniqueId())) {
                continue;
            }

            if (guild.getRegion().isInHeart(player.getLocation())) {
                continue;
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 5, 0));
        }
    }
}
