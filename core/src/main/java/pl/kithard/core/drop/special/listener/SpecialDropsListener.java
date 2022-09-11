package pl.kithard.core.drop.special.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.drop.special.SpecialDropItem;
import pl.kithard.core.drop.special.SpecialDropItemType;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.achievement.AchievementType;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.RandomUtil;
import pl.kithard.core.util.TextUtil;

public class SpecialDropsListener implements Listener {

    private final CorePlugin plugin;

    public SpecialDropsListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getItemMeta() == null || !itemInHand.getItemMeta().hasLore() || !itemInHand.getItemMeta().hasDisplayName()) {
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        if (itemInHand.getItemMeta().getLore().equals(CustomRecipe.MAGIC_CHEST.getItem().getItemMeta().getLore())) {

            if (corePlayer.getCombat().hasFight()) {
                event.setCancelled(true);
                return;
            }

            if (!this.plugin.getServerSettings().isEnabled(ServerSettingsType.MAGIC_CHEST)) {
                event.setCancelled(true);
                return;
            }

            corePlayer.addAchievementProgress(AchievementType.OPENED_CASE, 1);

            int i = 0;
            do {
                for (SpecialDropItem dropItem : this.plugin.getDropItemConfiguration().getSpecialDropItems()) {
                    if (dropItem.getType() != SpecialDropItemType.MAGIC_CHEST) {
                        continue;
                    }

                    if (i == 1) {
                        continue;
                    }

                    double chance = dropItem.getChance();
                    if (RandomUtil.getChance(chance)) {
                        i++;
                        ItemStack itemStack = dropItem.getItem().clone();
                        itemStack.setAmount(RandomUtil.getRandInt(dropItem.getMin(), dropItem.getMax()));
                        InventoryUtil.addItem(player, itemStack);
                        for (Player it : Bukkit.getOnlinePlayers()) {

                            CorePlayer itPlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
                            if (itPlayer.isDisabledSetting(PlayerSettings.MAGIC_CHEST_MESSAGES)) {
                                continue;
                            }

                            TextUtil.message(it, "&8» &7Gracz &f" + player.getName() + " &7otworzyl &b&lMagiczna Skrzynke &7i wydropil: &b" + dropItem.getName() + " &7(&f" + itemStack.getAmount() + "x&7)");
                        }
                    }
                }

            } while (i == 0);


            event.setCancelled(true);

            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            }
            else {
                player.setItemInHand(null);
            }

        }

        else if (itemInHand.getItemMeta().getDisplayName().equalsIgnoreCase(CustomRecipe.COBBLEX.getItem().getItemMeta().getDisplayName())) {

            if (corePlayer.getCombat().hasFight()) {
                event.setCancelled(true);
                return;
            }

            int i = 0;
            do {

                for (SpecialDropItem dropItem : this.plugin.getDropItemConfiguration().getSpecialDropItems()) {

                    if (dropItem.getType() != SpecialDropItemType.COBBLEX) {
                        continue;
                    }

                    if (i == 1) {
                        continue;
                    }

                    double chance = dropItem.getChance();
                    if (RandomUtil.getChance(chance)) {
                        i++;
                        ItemStack itemStack = dropItem.getItem().clone();
                        itemStack.setAmount(RandomUtil.getRandInt(dropItem.getMin(), dropItem.getMax()));
                        InventoryUtil.addItem(player, itemStack);
                        TextUtil.message(player, "&8» &7Otworzyles &2&lCobbleX &7i droplo ci: &b" + dropItem.getName() + " &7(&f" + itemStack.getAmount() + "x&7)");
                    }
                }

            } while (i == 0);

            event.setCancelled(true);

            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

        }


    }

}
