package pl.kithard.core.guild.bucket;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.LocationUtil;

import java.util.AbstractMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BucketInteractionListener implements Listener {

    private final static int MAX_Y = 60;
    public final static Map<Location, Map.Entry<UUID, Long>> WATER_CACHE = new ConcurrentHashMap<>();

    //get who spilled the water and the time of the event
    public static Map.Entry<UUID, Long> getData(Block block){
        return WATER_CACHE.get(block.getLocation());
    }

    //set who spilled the water and the time of the event
    public static void setData(UUID uuid, Block block){
        WATER_CACHE.put(block.getLocation(), new AbstractMap.SimpleEntry<>(uuid, System.currentTimeMillis()));
    }

    private final CorePlugin plugin;

    public BucketInteractionListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    /*
author FireBlastV
 */
    @EventHandler
    public void handle(BlockFromToEvent event){
        final Block block = event.getBlock();
        final Location location = block.getLocation();

        if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {


            //pre cancel
            event.setCancelled(true);

            //ignore if block is higher than y60
            if (location.getBlockY() > MAX_Y) {
                return;
            }

            final Guild guild = this.plugin.getGuildCache().findByLocation(location);

            //ignore if guild is null
            if (guild == null) {
                return;
            }

            final Map.Entry<UUID, Long> owner = getData(block);
            if (owner == null || guild.isMember(owner.getKey())) {
                event.setCancelled(false);
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void water(PlayerBucketEmptyEvent event) {
        if (LocationUtil.isInSpawn(event.getBlockClicked().getLocation())) {
            event.setCancelled(true);
            return;
        }

        if (event.getBucket() != Material.WATER_BUCKET) {
            return;
        }

        Player player = event.getPlayer();
        Block clickedBlock = event.getBlockClicked().getRelative(event.getBlockFace());
        setData(player.getUniqueId(), clickedBlock);

    }


}
