package pl.kithard.core.guild.logblock;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.generator.Generator;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

public class GuildLogBlockListener implements Listener {

    private final CorePlugin plugin;

    public GuildLogBlockListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        if (itemStack.getType() == Material.WOOD_PICKAXE && player.isSneaking()) {

            Block block = event.getClickedBlock();
            if (block == null || block.getType() == Material.AIR) {
                return;
            }

            Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
            if (guild == null) {
                TextUtil.message(player, "&8(&4&l!&8) &cLogblock dziala tylko na terenie gildii!");
                return;
            }

            new GuildLogBlockGui(this.plugin).open(player, block);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
        if (guild == null) {
            return;
        }

        Generator generator = this.plugin.getGeneratorCache().findByLocation(block.getLocation());
        if (generator != null) {
            return;
        }

        GuildLogBlock guildLogBlock = new GuildLogBlock(
                GuildLogBlockType.BREAK,
                player.getName(),
                block.getType(),
                block.getData(),
                block.getLocation(),
                System.currentTimeMillis()
        );

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin,
                () -> this.plugin.getGuildRepository().insertLogBlock(guildLogBlock)
        );
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
        if (guild == null) {
            return;
        }

        GuildLogBlock guildLogBlock = new GuildLogBlock(
                GuildLogBlockType.PLACE,
                player.getName(),
                block.getType(),
                block.getData(),
                block.getLocation(),
                System.currentTimeMillis()
        );

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin,
                () -> this.plugin.getGuildRepository().insertLogBlock(guildLogBlock)
        );
    }
}
