package pl.kithard.core.antigrief;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.LocationUtil;

public class AntiGriefListener implements Listener {

    private final CorePlugin plugin;

    public AntiGriefListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if (player.getItemInHand() == null && player.getItemInHand().getType() == Material.AIR) {
            return;
        }

        if (player.getItemInHand().getType() == Material.ENDER_STONE) {
            return;
        }

        if (LocationUtil.isInProtectionArea(player.getLocation())) {
            return;
        }

        if ((player.getWorld().getName().equals("world") && block.getY() < 60) || !block.getType().isSolid() || this.plugin.getGuildCache().findByLocation(block.getLocation()) != null) {
            return;
        }

        if (player.isSneaking() && player.getWorld().getName().equals("world")) {
            return;
        }

        this.plugin.getAntiGriefCache().getAntiGriefBlocks().add(new AntiGriefBlock(block));
    }
}
