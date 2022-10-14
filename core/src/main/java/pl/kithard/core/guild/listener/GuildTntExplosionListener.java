package pl.kithard.core.guild.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.generator.Generator;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.RandomUtil;
import pl.kithard.core.util.SquareUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuildTntExplosionListener implements Listener {

    private final CorePlugin plugin;

    public GuildTntExplosionListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        ServerSettings serverSettings = this.plugin.getServerSettings();
        if (TimeUtil.getHour(System.currentTimeMillis()) < 18 || TimeUtil.getHour(System.currentTimeMillis()) > 22) {
            event.setCancelled(true);
            return;
        }

        if (!serverSettings.isEnabled(ServerSettingsType.TNT)) {
            event.setCancelled(true);
            return;
        }

        List<Block> destroyedBlocks = event.blockList();
        destroyedBlocks.removeIf(block -> {
            Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());

            return guild == null || guild.getRegion().isInHeart(block.getLocation());
        });

        Guild guild = this.plugin.getGuildCache().findByLocation(event.getLocation());
        if (guild != null) {
            if (guild.getRegion().isInHeart(event.getLocation())) {
                event.setCancelled(true);
                return;
            }

            guild.setLastExplodeTime(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
            for (GuildMember onlineMember : guild.getOnlineMembers()) {
                this.plugin.getActionBarNoticeCache().add(onlineMember.getUuid(), ActionBarNotice
                        .builder()
                        .expireTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5))
                        .type(ActionBarNoticeType.TNT)
                        .text("&cNa terenie gildii wybuchlo &4TNT&c!")
                        .build());
            }
        }

        if (!destroyedBlocks.isEmpty()) {
            for (Block it : destroyedBlocks) {
                Generator generator = this.plugin.getGeneratorCache().findByLocation(it.getLocation());
                if (generator != null) {
                    this.plugin.getGeneratorFactory().delete(generator);
                    event.getLocation().getWorld().dropItemNaturally(event.getLocation(), CustomRecipe.STONE_GENERATOR.getItem());
                }
            }
        }

        List<Location> sphere = SquareUtil.sphere(event.getLocation(), 4, 4, false, true, 0);
        for (Location location : sphere) {

            Guild a = this.plugin.getGuildCache().findByLocation(location);
            if (a == null) {
                continue;
            }

            if (a.getRegion().isInHeart(location)) {
                continue;
            }

            if (location.getBlock().getType() == Material.OBSIDIAN) {
                if (RandomUtil.getChance(15)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.STATIONARY_WATER) {
                if (RandomUtil.getChance(20)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.STATIONARY_LAVA) {
                if (RandomUtil.getChance(20)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.WATER) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.LAVA) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.ENDER_CHEST) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
            if (location.getBlock().getType() == Material.ENCHANTMENT_TABLE) {
                if (RandomUtil.getChance(50)) {
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlaceWhenTnt(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Guild guild = this.plugin.getGuildCache().findByLocation(event.getBlock().getLocation());
        if (guild != null && guild.isMember(player.getUniqueId()) && guild.getLastExplodeTime() > System.currentTimeMillis()) {
            event.setCancelled(true);
            TextUtil.message(player, "&8(&4&l!&8) &cNie możesz budowac na terenie twojej gildi jeszcze przez &4" + TimeUtil.formatTimeMillis(guild.getLastExplodeTime() - System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void onPlaceTnt(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (event.getBlock().getType() == Material.TNT || event.getBlock().getType() == Material.DISPENSER) {
            if (block.getLocation().getY() > 50) {
                TextUtil.message(player, "&8(&4&l!&8) &cTNT i Dispensery mozna stawiac od 50y w doł!");
                event.setCancelled(true);
            }
        }
    }
}
