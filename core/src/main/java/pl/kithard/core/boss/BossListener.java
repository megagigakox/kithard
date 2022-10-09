package pl.kithard.core.boss;

import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.drop.special.SpecialDropItem;
import pl.kithard.core.drop.special.SpecialDropItemType;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.RandomUtil;
import pl.kithard.core.util.TextUtil;

public class BossListener implements Listener {

    private final CorePlugin plugin;

    public BossListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof IronGolem) {
            IronGolem zombie = (IronGolem) event.getEntity();
            if (!zombie.getUniqueId().equals(this.plugin.getBossService().getBoss().getUniqueId())) {
                return;
            }

            this.plugin.getBossService().setBoss(null);

            Player player = zombie.getKiller();
            if (player == null) {
                return;
            }

            int i = 0;
            do {
                for (SpecialDropItem dropItem : this.plugin.getDropItemConfiguration().getSpecialDropItems()) {
                    if (dropItem.getType() != SpecialDropItemType.BOSS) {
                        continue;
                    }

                    if (i == 3) {
                        continue;
                    }

                    double chance = dropItem.getChance();
                    if (RandomUtil.getChance(chance)) {
                        i++;
                        ItemStack itemStack = dropItem.getItem().clone();
                        itemStack.setAmount(RandomUtil.getRandInt(dropItem.getMin(), dropItem.getMax()));
                        InventoryUtil.addItem(player, itemStack);
                        for (Player it : Bukkit.getOnlinePlayers()) {
                            TextUtil.message(it, "");
                            TextUtil.message(it, "&8(&3&l!&8) &7Gracz &3" + player.getName() + " &7zabil &3&lbossa &7i wylosowal: &3" + dropItem.getName() + " &7(&f" + itemStack.getAmount() + "x&7)");
                            TextUtil.message(it, "");
                        }
                    }
                }

            } while (i == 0);
        }

    }

}
