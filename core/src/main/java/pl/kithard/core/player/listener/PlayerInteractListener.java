package pl.kithard.core.player.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.combat.PlayerCombat;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerInteractListener implements Listener {

    private final CorePlugin plugin;

    public PlayerInteractListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if (LocationUtil.isInSpawn(player.getLocation())) {
            if (!event.hasBlock()) {
                return;
            }

            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock.getType() != Material.WOOD_BUTTON) {
                return;
            }

            Button button = (Button) clickedBlock.getState().getData();
            Block relative = clickedBlock.getRelative(button.getAttachedFace());
            if (relative.getType() != Material.JUKEBOX) {
                return;
            }

            player.teleport(LocationUtil.getRadnomLocation());
        }
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

            Block ba = player.getLocation().getBlock();
            Block be = ba.getRelative(BlockFace.DOWN);

            if (!ba.getType().equals(Material.WOOD_PLATE) && !be.getType().equals(Material.WOOD_PLATE)) {
                return;
            }

            List<Player> playersInRadius = LocationUtil.getPlayersInRadius(event.getClickedBlock().getLocation(), 5, Material.WOOD_PLATE);
            if (playersInRadius.size() == 1) {
                TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz sie teleportowac sam!");
                return;
            }

            int i = 0;
            Location randomLocation = LocationUtil.getRadnomLocation();
            if (LocationUtil.isInSpawn(randomLocation)) {
                return;
            }

            for (Player radius : playersInRadius) {
                if (i == 2) {
                    return;
                }

                CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(radius);
                corePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
                corePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
                radius.getActivePotionEffects().forEach(potionEffect -> radius.removePotionEffect(potionEffect.getType()));
                radius.setAllowFlight(false);
                radius.teleport(randomLocation);
                i++;
            }
        }
    }

    @EventHandler
    public void onGtpAllVsAll(PlayerInteractEvent event) {
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

            Block ba = event.getPlayer().getLocation().getBlock();
            Block be = ba.getRelative(BlockFace.DOWN);
            if (!ba.getType().equals(Material.STONE_PLATE) && !be.getType().equals(Material.STONE_PLATE)) {
                return;
            }

            List<Player> playersInRadius = LocationUtil.getPlayersInRadius(event.getClickedBlock().getLocation(), 5, Material.STONE_PLATE);
            if (playersInRadius.size() == 1) {
                TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cNie mozesz sie teleportowac sam!");
                return;
            }

            Location randomLocation = LocationUtil.getRadnomLocation();
            if (LocationUtil.isInSpawn(randomLocation)) {
                return;
            }

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.teleport(randomLocation);
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
            corePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
            corePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
            for (Player radius : playersInRadius) {
                CorePlayer radiusCorePlayer = this.plugin.getCorePlayerCache().findByPlayer(radius);
                radiusCorePlayer.getCooldown().setGroupTeleportCombatCooldown(TimeUnit.SECONDS.toMillis(3) + System.currentTimeMillis());
                radiusCorePlayer.getCombat().setLastAttackTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(31));
                radius.teleport(player.getLocation());
                radius.setAllowFlight(false);
                radius.getActivePotionEffects().forEach(potionEffect -> radius.removePotionEffect(potionEffect.getType()));
            }
        }
    }

    @EventHandler
    public void onClickBoots(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand().getItemMeta() == null) {
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.ANTI_LEGS.getItem().getItemMeta().getDisplayName())
                && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            event.setCancelled(true);
            player.updateInventory();
            List<Entity> near = player.getNearbyEntities(3.0, 3.0, 3.0);
            for (Entity entity : near) {
                if (entity instanceof Player) {
                    Player nearest = (Player)entity;
                    PlayerCombat playerCombat = this.plugin.getCorePlayerCache().findByPlayer(player).getCombat();
                    PlayerCombat nearestCombat = this.plugin.getCorePlayerCache().findByPlayer(nearest).getCombat();
                    if (!playerCombat.hasFight() || !nearestCombat.hasFight()) {
                        continue;
                    }

                    player.teleport(nearest);
                    player.setItemInHand(null);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == null) return;

        if (event.getRightClicked() instanceof Player) {

            Player player = event.getPlayer();
            CorePlayer killerCorePlayer = plugin.getCorePlayerCache().findByPlayer(player);

            if (killerCorePlayer.getCooldown().getPointsInfoCooldown() > System.currentTimeMillis()) {
                return;
            }

            CorePlayer clickedCorePlayer = plugin.getCorePlayerCache().findByPlayer((Player) event.getRightClicked());
            int toAddClicked = (int) (43.0 + (clickedCorePlayer.getPoints() - killerCorePlayer.getPoints()) * -0.10);
            if (toAddClicked < 10) {
                toAddClicked = 8;
            }
            int toRemoveClicked = (int) (toAddClicked / 1.7);
            if (toRemoveClicked < 5) {
                toRemoveClicked = 5;
            }

            int add = (int) (43.0 + (killerCorePlayer.getPoints() - clickedCorePlayer.getPoints()) * -0.10);
            if (add < 10) {
                add = 8;
            }

            killerCorePlayer.getCooldown().setPointsInfoCooldown(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3));
            TextUtil.message(player, "&7Jezeli zabijesz tego gracza dostaniesz&8: &b&l+"
                    + (clickedCorePlayer.getCooldown().getLastKillersCooldown().containsKey(killerCorePlayer.getUuid()) ? "0" : add)
                    + " &7punktow!");
            TextUtil.message(player, "&7Natomiast za zginiecie zostanie ci odebrane&8: &c&l-"
                    + (killerCorePlayer.getCooldown().getLastKillersCooldown().containsKey(clickedCorePlayer.getUuid()) ? "0" : toRemoveClicked)
                    + " &7punktow!");
        }
    }
}

