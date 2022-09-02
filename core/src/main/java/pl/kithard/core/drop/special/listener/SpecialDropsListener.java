package pl.kithard.core.drop.special.listener;

import org.apache.commons.lang.StringUtils;
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
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.RandomUtil;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            List<SpecialDropItem> dropItems = new ArrayList<>();
            for (SpecialDropItem dropItem : this.plugin.getDropItemCache().getSpecialDropItems()) {

                if (dropItem.getType() != SpecialDropItemType.MAGIC_CHEST) {
                    continue;
                }

                double chance = dropItem.getChance();
                if (!RandomUtil.getChance(chance)) {
                    continue;
                }

                if (dropItems.size() == 2) {
                    break;
                }

                dropItems.add(dropItem);

            }

            for (SpecialDropItem specialDropItem : dropItems) {

                InventoryUtil.addItem(player, specialDropItem.getItem());

            }

            for (Player it : Bukkit.getOnlinePlayers()) {

                CorePlayer itPlayer = this.plugin.getCorePlayerCache().findByPlayer(it);
                if (itPlayer.isDisabledSetting(PlayerSettings.MAGIC_CHEST_MESSAGES)) {
                    continue;
                }

                TextUtil.message(it, "&8» &7Gracz &f" + player.getName() + " &7otworzyl &bMagiczna Skrzynke &7i wydropil: &b" +
                        (dropItems.isEmpty()
                                ? "Nic :("
                                : StringUtils.join(dropItems.stream().map(SpecialDropItem::getName).collect(Collectors.toList()), "&8,&b ")));
            }

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

            List<SpecialDropItem> dropItems = new ArrayList<>();
            for (SpecialDropItem dropItem : this.plugin.getDropItemCache().getSpecialDropItems()) {

                if (dropItem.getType() != SpecialDropItemType.COBBLEX) {
                    continue;
                }

                double chance = dropItem.getChance();
                if (!RandomUtil.getChance(chance)) {
                    continue;
                }

                if (dropItems.size() == 1) {
                    break;
                }

                dropItems.add(dropItem);

            }

            for (SpecialDropItem specialDropItem : dropItems) {
                InventoryUtil.addItem(player, specialDropItem.getItem());
            }

            TextUtil.message(player, "&8» &7Otworzyles &2CobbleX &7i droplo ci: &b" +
                    (dropItems.isEmpty()
                            ? "Nic :("
                            : StringUtils.join(dropItems.stream()
                            .map(SpecialDropItem::getName)
                            .collect(Collectors.toList()), "&8,&b ")));

            event.setCancelled(true);

            if (itemInHand.getAmount() > 1) {
                itemInHand.setAmount(itemInHand.getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }

        }


    }

}
