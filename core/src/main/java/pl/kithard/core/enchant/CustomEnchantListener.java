package pl.kithard.core.enchant;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.TextUtil;

public class CustomEnchantListener implements Listener {

    private final CorePlugin plugin;

    public CustomEnchantListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                return;
            }

            CustomEnchantType customEnchantType = this.plugin.getCustomEnchantConfiguration().findType(player.getItemInHand().getType().name());
            if (customEnchantType == CustomEnchantType.ARMOR || customEnchantType == CustomEnchantType.BOW || customEnchantType == CustomEnchantType.SWORD || customEnchantType == CustomEnchantType.BOOTS) {
                if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.ENCHANT)) {
                    TextUtil.message(player, "&cEnchantowanie tych przedmiotow zostalo tymczasowo wylaczone do godziny 12:00 Niedziela");
                    return;
                }
            }
            if (customEnchantType == null) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz tego zenchantowac!");
                return;
            }

            new CustomEnchantGui(plugin).open(player, customEnchantType);
        }
    }
}
