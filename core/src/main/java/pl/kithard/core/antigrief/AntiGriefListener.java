package pl.kithard.core.antigrief;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

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

        if (block.getY() < 60 || !block.getType().isSolid() || player.isSneaking() || this.plugin.getGuildCache().findByLocation(block.getLocation()) != null) {
            return;
        }

        this.plugin.getAntiGriefCache().getAntiGriefBlocks().add(new AntiGriefBlock(block));
        TextUtil.message(player, "&b&lANTI-GRIEF &cTen blok zniknie za 10 minut, aby tego uniknac postaw blok trzymajac &c&lSHIFT&c.");
    }
}
