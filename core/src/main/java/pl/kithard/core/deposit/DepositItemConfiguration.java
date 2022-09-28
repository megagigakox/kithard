package pl.kithard.core.deposit;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;

import java.util.ArrayList;
import java.util.List;

public class DepositItemConfiguration extends OkaeriConfig {

    private List<DepositItem> depositItems = new ArrayList<>();

    public DepositItemConfiguration() {
        this.depositItems.add(new DepositItem(
                "Koxy",
                1,
                true,
                20,
                "&8(&3&l!&8) &7Przeniesiono nadmiar &8(&f{AMOUNT}&8) &7koxow do schowka! &7Aktualny stan w schowku: &f{OWNED-AMOUNT}",
                new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1)
        ));
        this.depositItems.add(new DepositItem(
                "Refile",
                1,
                true,
                21,
                "&8(&3&l!&8) &7Przeniesiono nadmiar &8(&f{AMOUNT}&8) &7refow do schowka! &7Aktualny stan w schowku: &f{OWNED-AMOUNT}",
                new ItemStack(Material.GOLDEN_APPLE)
        ));
        this.depositItems.add(new DepositItem(
                "Perly",
                1,
                true,
                22,
                "&8(&3&l!&8) &7Przeniesiono nadmiar &8(&f{AMOUNT}&8) &7perel do schowka! &7Aktualny stan w schowku: &f{OWNED-AMOUNT}",
                new ItemStack(Material.ENDER_PEARL)
        ));
        this.depositItems.add(new DepositItem(
                "Strzaly",
                16,
                true,
                23,
                "&8(&3&l!&8) &7Przeniesiono nadmiar &8(&f{AMOUNT}&8) &7strzal do schowka! &7Aktualny stan w schowku: &f{OWNED-AMOUNT}",
                new ItemStack(Material.ARROW)
        ));
        this.depositItems.add(new DepositItem(
                "Sniezki",
                16,
                true,
                24,
                "&8(&3&l!&8) &7Przeniesiono nadmiar &8(&f{AMOUNT}&8) &7sniezek do schowka! &7Aktualny stan w schowku: &f{OWNED-AMOUNT}",
                new ItemStack(Material.SNOW_BALL)
        ));
    }

//    public void init() {
//        this.depositItems.clear();
//        Configuration configuration = this.plugin.getDepositConfiguration().getCustomConfig();
//
//        for (String id : configuration.getConfigurationSection("deposit-items").getKeys(false)) {
//
//            String[] split = configuration.getString("deposit-items." + id + ".item.type").split(":");
//
//            int itemId = Integer.parseInt(split[0]);
//            int amount = Integer.parseInt(split[1]);
//            byte data = Byte.parseByte(split[2]);
//
//            ItemStack itemStack = ItemStackBuilder.of(new ItemStack(itemId, amount, data))
//                    .name(configuration.getString("deposit-items." + id + ".item.name"))
//                    .lore(configuration.getStringList("deposit-items." + id + ".item.lore"))
//                    .glow(configuration.getBoolean("deposit-items." + id + ".item.glow"))
//                    .asItemStack();
//
//            DepositItem depositItem = new DepositItem(
//                    id,
//                    configuration.getString("deposit-items." + id + ".name"),
//                    configuration.getInt("deposit-items." + id + ".limit"),
//                    configuration.getBoolean("deposit-items." + id + ".withdraw-all"),
//                    configuration.getInt("deposit-items." + id + ".slot"),
//                    configuration.getString("deposit-items." + id + ".message"),
//                    itemStack);
//
//            this.depositItems.add(depositItem);
//        }
//    }

    public List<DepositItem> getDepositItems() {
        return depositItems;
    }
}
