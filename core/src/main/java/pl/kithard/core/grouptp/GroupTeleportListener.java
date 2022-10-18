package pl.kithard.core.grouptp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.material.Button;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.RandomUtil;
import pl.kithard.core.util.TextUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GroupTeleportListener implements Listener {

    private final CorePlugin plugin;

    public GroupTeleportListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGtpOneVsOne(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (LocationUtil.isInSpawn(player.getLocation())) {

            if (!event.hasBlock()) {
                return;
            }

            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock.getType() != Material.STONE_BUTTON) {
                return;
            }

            Button button = (Button) clickedBlock.getState().getData();
            Block blockRelative = clickedBlock.getRelative(button.getAttachedFace());
            if (blockRelative.getType() != Material.JUKEBOX) {
                return;
            }

            Block block = player.getLocation().getBlock();
            Block relative = block.getRelative(BlockFace.DOWN);
            if (block.getType() != Material.WOOD_PLATE && relative.getType() != Material.WOOD_PLATE) {
                return;
            }

            List<Player> playersInRadius = LocationUtil.getPlayersInRadius(event.getClickedBlock().getLocation(), 5, Material.WOOD_PLATE);
            if (playersInRadius.size() == 1) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz sie teleportowac sam!");
                return;
            }

            int i = 0;
            Location randomLocation = getGtpRandomLocation();
            GroupTeleport groupTeleport = new GroupTeleport(randomLocation);
            for (Player it : playersInRadius) {
                if (i == 2) {
                    return;
                }

                groupTeleport.getPlayers().add(it);
                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
                corePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
                corePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
                it.getActivePotionEffects().forEach(potionEffect -> it.removePotionEffect(potionEffect.getType()));
                it.teleport(randomLocation);
                i++;
            }

            groupTeleport.prepareBorder();
            this.plugin.getGroupTeleportCache().add(groupTeleport);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.getWorld().getName().equals("gtp") && block.getY() == 3) {
            if (!player.hasPermission("gtp.break.bypass")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getName().equals("gtp")) {
            GroupTeleport groupTeleport = this.plugin.getGroupTeleportCache().find(player);
            if (groupTeleport == null) {
                return;
            }

            this.plugin.getGroupTeleportCache().remove(player, groupTeleport);
            for (Player groupTeleportPlayer : groupTeleport.getPlayers()) {
                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(groupTeleportPlayer);
                corePlayer.getCombat().setLastAttackTime(0L);
            }

            groupTeleport.setCloseCountdownTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(45));
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("gtp")) {
            GroupTeleport groupTeleport = this.plugin.getGroupTeleportCache().find(player);
            if (groupTeleport == null) {
                return;
            }

            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
            this.plugin.getGroupTeleportCache().remove(player, groupTeleport);
            for (Player groupTeleportPlayer : groupTeleport.getPlayers()) {
                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(groupTeleportPlayer);
                corePlayer.getCombat().setLastAttackTime(0L);
            }

            groupTeleport.setCloseCountdownTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(45));
        }

    }

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        if (!event.getPlayer().getWorld().getName().equals("gtp")) {
            return;
        }

        GroupTeleport groupTeleport = this.plugin.getGroupTeleportCache().find(event.getPlayer());
        if (groupTeleport == null) {
            return;
        }

        if (!groupTeleport.getGroupTeleportBorder().isInside(event.getTo())) {
            event.setCancelled(true);
        }
    }

//    @EventHandler
//    public void onGtpOneVsOne(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        if (LocationUtil.isInSpawn(player.getLocation())) {
//
//            if (!event.hasBlock()) {
//                return;
//            }
//
//            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
//                return;
//            }
//
//            Block clickedBlock = event.getClickedBlock();
//            if (clickedBlock.getType() != Material.STONE_BUTTON) {
//                return;
//            }
//
//            Button button = (Button) clickedBlock.getState().getData();
//            Block blockRelative = clickedBlock.getRelative(button.getAttachedFace());
//            if (blockRelative.getType() != Material.JUKEBOX) {
//                return;
//            }
//
//            Block ba = player.getLocation().getBlock();
//            Block be = ba.getRelative(BlockFace.DOWN);
//            if (!ba.getType().equals(Material.WOOD_PLATE) && !be.getType().equals(Material.WOOD_PLATE)) {
//                return;
//            }
//
//            List<Player> playersInRadius = LocationUtil.getPlayersInRadius(event.getClickedBlock().getLocation(), 5, Material.WOOD_PLATE);
//            if (playersInRadius.size() == 1) {
//                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz sie teleportowac sam!");
//                return;
//            }
//
//            int i = 0;
//            Location randomLocation = LocationUtil.getRadnomLocation();
//
//            for (Player radius : playersInRadius) {
//                if (i == 2) {
//                    return;
//                }
//
//                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(radius);
//                corePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
//                corePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
//                radius.getActivePotionEffects().forEach(potionEffect -> radius.removePotionEffect(potionEffect.getType()));
//                radius.setAllowFlight(false);
//                radius.teleport(randomLocation);
//                i++;
//            }
//        }
//    }

//    @EventHandler
//    public void onGtpAllVsAll(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//
//        if (LocationUtil.isInSpawn(player.getLocation())) {
//
//            if (!event.hasBlock()) {
//                return;
//            }
//
//            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
//                return;
//            }
//
//            Block clickedBlock = event.getClickedBlock();
//            if (clickedBlock.getType() != Material.STONE_BUTTON) {
//                return;
//            }
//
//            Button button = (Button) clickedBlock.getState().getData();
//            Block blockRelative = clickedBlock.getRelative(button.getAttachedFace());
//            if (blockRelative.getType() != Material.JUKEBOX) {
//                return;
//            }
//
//            Block ba = event.getPlayer().getLocation().getBlock();
//            Block be = ba.getRelative(BlockFace.DOWN);
//            if (!ba.getType().equals(Material.STONE_PLATE) && !be.getType().equals(Material.STONE_PLATE)) {
//                return;
//            }
//
//            List<Player> playersInRadius = LocationUtil.getPlayersInRadius(event.getClickedBlock().getLocation(), 5, Material.STONE_PLATE);
//            if (playersInRadius.size() == 1) {
//                TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cNie mozesz sie teleportowac sam!");
//                return;
//            }
//
//            Location randomLocation = LocationUtil.getRadnomLocation();
//            if (LocationUtil.isInSpawn(randomLocation)) {
//                return;
//            }
//
//            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
//            player.teleport(randomLocation);
//            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
//            corePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
//            corePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
//            for (Player radius : playersInRadius) {
//                CorePlayer radiusCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(radius);
//                radiusCorePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
//                radiusCorePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
//                radius.teleport(player.getLocation());
//                radius.setAllowFlight(false);
//                radius.getActivePotionEffects().forEach(potionEffect -> radius.removePotionEffect(potionEffect.getType()));
//            }
//        }
//    }

    public static Location getGtpRandomLocation() {
        World world = Bukkit.getWorld("gtp");
        int x = RandomUtil.getRandInt(-1_000_00, 1_000_00);
        int z = RandomUtil.getRandInt(-1_000_00, 1_000_00);
        double y = (world.getHighestBlockYAt(x, z) + 1.5F);
        return new Location(world, x, y, z);
    }

}
