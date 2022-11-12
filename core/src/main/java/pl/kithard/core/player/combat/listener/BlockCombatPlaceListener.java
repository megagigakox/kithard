package pl.kithard.core.player.combat.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BlockCombatPlaceListener implements Listener {

    private final static Map<UUID, Long> LAST_SLIME_PLACE = new HashMap<>();
    private final CorePlugin plugin;

    public BlockCombatPlaceListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlaceAntilogout(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockPlaced().getLocation());
        Block block = event.getBlock();

        if (player.getWorld().getName().equals("gtp") && block.getY() > 5) {
            event.setCancelled(true);
            return;
        }

        if (guild != null && guild.isMember(player.getUniqueId())) {
            if (corePlayer.getCombat().hasFight()) {
                event.setCancelled(true);
                TextUtil.message(player, "&8(&4&l!&8) &cBudowanie podczas pvp na terenie swojej gildii jest zablokowane!");
                return;
            }

            if (block.getLocation().getBlockY() > 50 && (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
                event.setCancelled(true);
                TextUtil.message(player, "&8(&4&l!&8) &cSkrzynki mozesz stawiac dopiero od 50Y w dol!");
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlaceSlime(BlockPlaceEvent event) {

        if (!event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block == null || block.getType() != Material.SLIME_BLOCK) {
            return;
        }

        LAST_SLIME_PLACE.put(player.getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityDamageEvent event) {

        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.SLIME_BLOCK) {

            long lastTimePlaced = LAST_SLIME_PLACE.get(player.getUniqueId());
            if (lastTimePlaced == 0) {
                return;
            }

            if (lastTimePlaced + TimeUnit.MILLISECONDS.toMillis(100) < System.currentTimeMillis()) {
                return;
            }

            player.setVelocity(new Vector(0, 2.05, 0));
        }
    }
}
