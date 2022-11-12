package pl.kithard.core.antimacro;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TitleUtil;

public class AntiMacroListener implements Listener {

    private final CorePlugin plugin;

    public AntiMacroListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {

            if (event.getItem() != null && event.getItem().getType().toString().contains("PICKAXE")) {
                return;
            }

            Player player = event.getPlayer();
            int cps = this.plugin.getAntiMacroCache().getUuidClicksPerSecondMap().get(player.getUniqueId());

            if (cps >= 17) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0));
                TitleUtil.title(player, "&3&lANTI-MACRO", "&cPrzekroczyles limit cps! &cAktualny limit wynosi: &b15", 0, 60, 20);
            } else {
                this.plugin.getAntiMacroCache().getUuidClicksPerSecondMap().put(player.getUniqueId(), cps + 1);
            }
        }

    }
}
