package pl.kithard.core.guild.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.concurrent.TimeUnit;

public class GuildHeartListener implements Listener {

    private final CorePlugin plugin;

    public GuildHeartListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void inHeartBreak(BlockBreakEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlock().getLocation());

        if (guild == null) {
            return;
        }

        if (guild.getRegion().isInHeart(event.getBlock().getLocation())) {
            event.setCancelled(true);
            TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cNie możesz niszczyc w sercu gildii!");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void inHeartPlace(BlockPlaceEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlock().getLocation());

        if (guild == null) {
            return;
        }

        if (guild.getRegion().isInHeart(event.getBlock().getLocation())) {
            event.setCancelled(true);
            TextUtil.message(event.getPlayer(), "&8(&4&l!&8) &cNie możesz stawiac w sercu gildii!");
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void conquer(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.SEA_LANTERN) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
        if (guild == null) {
            return;
        }

        if (!guild.getRegion().isInHeart(block.getLocation())) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
        Guild playerGuild = this.plugin.getGuildCache().findByPlayer(player);

        if (playerGuild == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz swojej gildii aby moc podbic wroga gildie!");
            return;
        }

        if (guild.isMember(player.getUniqueId())) {
            return;
        }

        if (guild.getLastAttackTime() > System.currentTimeMillis()) {
            TextUtil.message(player, "&8(&4&l!&8) &cTa gildie możesz podbic dopiero za &4" +
                    TimeUtil.formatTimeMillis(guild.getLastAttackTime() - System.currentTimeMillis()));
            return;
        }


        if (guild.getLives() == 1) {

            Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getGuildFactory().delete(guild));

            if (playerGuild.getLives() < 3) {
                playerGuild.setLives(playerGuild.getLives() + 1);
                playerGuild.setNeedSave(true);
            }

            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Gildia &8[&b" + guild.getTag() + "&8] &7zostala zniszczona przez &8[&b" + playerGuild.getTag() + "&8] &f" + player.getName()));

        } else {

            guild.setLastAttackTime(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24));
            guild.setLives(guild.getLives() - 1);
            guild.setNeedSave(true);

            if (playerGuild.getLives() < 3) {
                playerGuild.setLives(playerGuild.getLives() + 1);
            }
            playerGuild.setNeedSave(true);

            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Gildia &8[&b" + guild.getTag() + "&8] &7zostala oslabiona o jedno życie przez &8[&b" + playerGuild.getTag() + "&8] &f" + player.getName()));
        }

    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType() != Material.SEA_LANTERN) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
        if (guild == null) {
            return;
        }

        if (!guild.getRegion().isInHeart(block.getLocation())) {
            return;
        }

        Player player = event.getPlayer();
        if (guild.isMember(player.getUniqueId()) && guild.isDeputyOrOwner(player.getUniqueId())) {
            new GuildPanelGui(plugin).openPanel(player, guild);
            return;
        }

        guild.openWarehouse(player);

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getToBlock().getLocation());
        if (guild == null) {
            return;
        }

        if (!guild.getRegion().isInHeart(event.getToBlock().getLocation())) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEmpty(PlayerBucketEmptyEvent event) {
        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlockClicked().getLocation());
        if (guild == null) {
            return;
        }

        if (!guild.getRegion().isInHeart(event.getBlockClicked().getLocation())) {
            return;
        }

        event.setCancelled(true);
    }

}