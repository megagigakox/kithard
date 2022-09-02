package pl.kithard.core.deposit;

import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.ArrayList;
import java.util.List;

public class DepositItemCache {
    private final CorePlugin plugin;

    private final List<DepositItem> depositItems = new ArrayList<>();

    public DepositItemCache(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.depositItems.clear();
        Configuration configuration = this.plugin.getDepositConfiguration().getCustomConfig();

        for (String id : configuration.getConfigurationSection("deposit-items").getKeys(false)) {

            String[] split = configuration.getString("deposit-items." + id + ".item.type").split(":");

            int itemId = Integer.parseInt(split[0]);
            int amount = Integer.parseInt(split[1]);
            byte data = Byte.parseByte(split[2]);

            ItemStack itemStack = ItemStackBuilder.of(new ItemStack(itemId, amount, data))
                    .name(configuration.getString("deposit-items." + id + ".item.name"))
                    .lore(configuration.getStringList("deposit-items." + id + ".item.lore"))
                    .glow(configuration.getBoolean("deposit-items." + id + ".item.glow"))
                    .asItemStack();

            DepositItem depositItem = new DepositItem(
                    id,
                    configuration.getString("deposit-items." + id + ".name"),
                    configuration.getInt("deposit-items." + id + ".limit"),
                    configuration.getBoolean("deposit-items." + id + ".withdraw-all"),
                    configuration.getInt("deposit-items." + id + ".slot"),
                    configuration.getString("deposit-items." + id + ".message"),
                    itemStack);

            this.depositItems.add(depositItem);
        }
    }

    public List<DepositItem> getDepositItems() {
        return depositItems;
    }
}
