package pl.kithard.core.drop.listener;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.drop.util.DropUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemUtil;
import pl.kithard.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemDropListener implements Listener {

    private final CorePlugin plugin;

    public ItemDropListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        ServerSettings serverSettings = this.plugin.getServerSettings();
        ItemStack itemInHand = player.getItemInHand();

        List<ItemStack> drops = new ArrayList<>();
        if (block.getType() != Material.STONE) {
            drops.addAll(block.getDrops(itemInHand));

            event.setCancelled(true);
            block.setType(Material.AIR);

            InventoryUtil.addItem(player, drops);
            ItemUtil.calculateDurability(player);
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        if (!corePlayer.isDisabledSetting(PlayerSettings.COBBLE_DROP)) {
            drops.add(new ItemStack(itemInHand.containsEnchantment(Enchantment.SILK_TOUCH) ? Material.STONE : Material.COBBLESTONE, 1));
        }

        Guild guild = this.plugin.getGuildCache().findByPlayer(player);
        for (DropItem dropItem : this.plugin.getDropItemConfiguration().getDropItems()) {
            double chance = DropUtil.calculateChanceFromStone(dropItem, corePlayer, serverSettings, guild);

            if (!RandomUtil.getChance(chance)) {
                continue;
            }

            int amount = 1;
            if (dropItem.isFortune() && itemInHand.getType().toString().contains("PICKAXE")) {

                if (dropItem.getItem().getEnchantmentLevel(Enchantment.DIG_SPEED) == 6) {
                    amount = ItemUtil.getFortuneLevel(itemInHand) == 0 ? 1 : RandomUtil.getRandInt(1, 2);
                }
                else {
                    amount = ItemUtil.getFortuneLevel(itemInHand) == 0 ? 1 : RandomUtil.getRandInt(1, ItemUtil.getFortuneLevel(itemInHand));
                }

            }

            if (!corePlayer.isDisabledDropItem(dropItem)) {

                ItemStack item = dropItem.getItem();
                item.setAmount(amount);

                drops.add(item);

                corePlayer.addMinedDropItem(dropItem, amount);
                corePlayer.setNeedSave(true);
                player.giveExp(dropItem.getExp());

                if (!corePlayer.isDisabledSetting(PlayerSettings.DROP_MESSAGES)) {
                    this.plugin.getActionBarNoticeCache().add(
                            player.getUniqueId(),
                            ActionBarNotice.builder()
                                    .type(ActionBarNoticeType.STONE_DROP)
                                    .text("&8» &7Wydropiles&8: &b" + dropItem.getItem().getType().toString() + "&7(&f" + dropItem.getItem().getAmount() + "x&8,&f " + dropItem.getExp() + "exp&7)")
                                    .expireTime(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(2))
                                    .build()
                    );
                }
            }
        }

        player.giveExp(5);
        event.setCancelled(true);
        block.setType(Material.AIR);

        InventoryUtil.addItem(player, drops);
        ItemUtil.calculateDurability(player);
    }
}
