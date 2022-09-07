package pl.kithard.core.player.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
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
                TextUtil.message(event.getPlayer(), "&8[&4&l!&8] &cNie mozesz sie teleportowac sam!");
                return;
            }

            randomTeleport(playersInRadius, 2);
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
                TextUtil.message(event.getPlayer(), "&8[&4&l!&8] &cNie mozesz sie teleportowac sam!");
                return;
            }

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            player.teleport(LocationUtil.getRadnomLocation());
            for (Player it : playersInRadius) {
                it.teleport(player.getLocation());
                it.setAllowFlight(false);
                it.getActivePotionEffects().forEach(potionEffect -> it.removePotionEffect(potionEffect.getType()));
            }
        }
    }

    public void randomTeleport(List<Player> players, int i) {
        int a = 0;

        for (Player target : players) {
            if (a == i) {
                return;
            }

            target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));
            target.setAllowFlight(false);
            target.teleport(LocationUtil.getRadnomLocation());
            a++;
        }
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == null) return;

        if (event.getRightClicked() instanceof Player) {

            Player player = event.getPlayer();
            CorePlayer killerCorePlayer = plugin.getCorePlayerCache().findByPlayer(player);

            if (killerCorePlayer.getCooldown().getPointsInfoDelay() > System.currentTimeMillis()) {
                return;
            }

            CorePlayer clickedCorePlayer = plugin.getCorePlayerCache().findByPlayer((Player) event.getRightClicked());

            int toAddClicked = (int) (43.0 + (clickedCorePlayer.getPoints() - killerCorePlayer.getPoints()) * -0.25);
            if (toAddClicked <= 20) {
                toAddClicked = 20;
            }

            int toRemoveClicked = toAddClicked / 2;
            if (toAddClicked / 2 <= 10) {
                toRemoveClicked = 10;
            }

            int add = (int) (43.0 + (killerCorePlayer.getPoints() - clickedCorePlayer.getPoints()) * -0.25);
            if (add <= 20) {
                add = 20;
            }

            killerCorePlayer.getCooldown().setPointsInfoDelay(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));

            TextUtil.message(player, "");
            TextUtil.message(player, " &8» &7Za zabicie tego gracza dostaniesz &b&l+" + (clickedCorePlayer.getLastDeaths().containsKey(killerCorePlayer.getUuid()) ? "0" : add) + " &7punktow!");
            TextUtil.message(player, " &8» &7Za zginiecie od tego gracza stracisz &c&l-" + (killerCorePlayer.getLastDeaths().containsKey(clickedCorePlayer.getUuid()) ? "0" : toRemoveClicked) + " &7punktow!");
            TextUtil.message(player, "");
        }
    }
}

