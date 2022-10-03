package pl.kithard.core.guild.warehouse;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class WarehouseListener implements Listener {

    private final CorePlugin plugin;

    public WarehouseListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getName().contains("Magazyn gildii")) {
            String[] split = ChatColor.stripColor(inventory.getName()).split(":\\s*");
            String tag = split[1];
            Guild guild = this.plugin.getGuildCache().findByTag(tag);
            if (inventory.getContents() != null) {
                guild.setWarehouseContents(inventory.getContents());
            }

            guild.setNeedSave(true);
        }
    }
}
