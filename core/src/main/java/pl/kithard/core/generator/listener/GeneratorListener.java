package pl.kithard.core.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.generator.Generator;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

public class GeneratorListener implements Listener {

    private final CorePlugin plugin;

    public GeneratorListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Generator generator = this.plugin.getGeneratorCache().findByLocation(block.getLocation());
        if (generator == null) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getType() == Material.GOLD_PICKAXE) {
            if (LocationUtil.isInSpawn(block.getLocation()) && !player.hasPermission("kithard.generator.bypass")) {
                event.setCancelled(true);
                return;
            }

            Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
            if (guild != null && guild.isMember(player.getUniqueId()) && this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.END_STONE_BREAK)) {
                event.setCancelled(true);
                return;
            }

            TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie usunieto generator kamienia!");
            this.plugin.getGeneratorFactory().delete(generator);
            InventoryUtil.addItem(player, CustomRecipe.STONE_GENERATOR.getItem());
            return;
        }

        this.plugin.getGeneratorCache().regen(generator);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand.getItemMeta() == null || itemInHand.getItemMeta().getDisplayName() == null) {
            return;
        }

        if (!itemInHand.getItemMeta().getDisplayName()
                .equalsIgnoreCase(CustomRecipe.STONE_GENERATOR.getItem().getItemMeta().getDisplayName())) {
            return;
        }

        Block block = event.getBlock();
        Generator generator = this.plugin.getGeneratorCache().findByLocation(block.getLocation());
        if (generator == null) {
            Guild guild = this.plugin.getGuildCache().findByLocation(block.getLocation());
            if (guild != null && guild.isMember(player.getUniqueId()) && this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.END_STONE_PLACEMENT)) {
                event.setCancelled(true);
                return;
            }

            this.plugin.getGeneratorFactory().create(block.getLocation());
            event.getBlock().setType(Material.STONE);
            TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &astworzono &7generator kamienia!");
            return;
        }

        TextUtil.message(player, "&8(&4&l!&8) &cW tym miejscu &4jest juz generator&c!");
        event.setCancelled(true);
        this.plugin.getGeneratorCache().regen(generator);
    }
}
