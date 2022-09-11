package pl.kithard.core.itemshop;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Exclude;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.util.*;

public class ItemShopServiceConfiguration extends OkaeriConfig {

    private List<ItemShopService> services = new ArrayList<>();

    public ItemShopServiceConfiguration() {
        this.services.add(new ItemShopService(
                "vip",
                Collections.singletonList("lp user {PLAYER} parent set vip"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &eVIP &7na zawsze!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "vipedycja",
                Collections.singletonList("lp user {PLAYER} parent addtemp vip 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &eVIP &7na edycje!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "svip",
                Collections.singletonList("lp user {PLAYER} parent set svip"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &6SVIP &7na zawsze!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "svipedycja",
                Collections.singletonList("lp user {PLAYER} parent addtemp svip 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &6SVIP &7na edycje!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "sponsor",
                Collections.singletonList("lp user {PLAYER} parent set sponsor"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &bSponsor &7na zawsze!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "sponsoredycja",
                Collections.singletonList("lp user {PLAYER} parent addtemp sponsor 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &bSponsor &7na edycje!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "hard",
                Collections.singletonList("lp user {PLAYER} parent set hard"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &3HARD &7na zawsze!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "hardedycja",
                Collections.singletonList("lp user {PLAYER} parent addtemp hard 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &3HARD &7na edycje!",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "case32",
                new ArrayList<>(),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l32x Magiczne Skrzynki",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                ),
                Collections.singletonList(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone())
                        .amount(32)
                        .asItemStack())));

        this.services.add(new ItemShopService(
                "case64",
                new ArrayList<>(),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l64x Magiczne Skrzynki",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                        ),
                Collections.singletonList(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone())
                        .amount(64)
                        .asItemStack())));

        this.services.add(new ItemShopService(
                "case128",
                new ArrayList<>(),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l128x Magiczne Skrzynki",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                        ),
                Collections.singletonList(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone())
                        .amount(128)
                        .asItemStack())));

        this.services.add(new ItemShopService(
                "case256",
                new ArrayList<>(),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l256x Magiczne Skrzynki",
                        " &7Nasz itemshop serwerowy: &bhttps://kithard.pl/",
                        ""
                        ),
                Collections.singletonList(ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone())
                        .amount(256)
                        .asItemStack())));
    }

//    public void init() {
//        this.services.clear();
//        Configuration config = this.plugin.getItemShopServiceConfiguration().getCustomConfig();
//
//        for (String name : config.getConfigurationSection("services").getKeys(false)) {
//            ItemShopService itemShopService = new ItemShopService(name,
//                    config.getStringList("services." + name + ".commands"),
//                    config.getStringList("services." + name + ".messages"));
//
//            this.services.put(name, itemShopService);
//
//            if (config.getConfigurationSection("services." + name + ".items") != null) {
//                for (String item : config.getConfigurationSection("services." + name + ".items").getKeys(false)) {
//
//                    List<String> enchantList = config.getStringList("services." + name + ".items." + item + ".enchantments");
//                    String[] split = config.getString("services." + name + ".items." + item + ".type").split(":");
//
//                    ItemStack itemStack = ItemBuilder.from(new ItemStack(Material.getMaterial(split[0]), Integer.parseInt(split[1])))
//                            .name(TextUtil.component(config.getString("services." + name + ".items." + item + ".name")))
//                            .lore(TextUtil.component(config.getStringList("services." + name + ".items." + item + ".lore")))
//                            .build();
//
//                    if (enchantList.size() > 0) {
//                        for (String enchant : enchantList) {
//
//                            String[] enchantArray = enchant.split(":");
//                            String enchantment = enchantArray[0];
//                            int enchantMultiplier = Integer.parseInt(enchantArray[1]);
//
//                            itemStack.addUnsafeEnchantment(Enchantment.getByName(enchantment), enchantMultiplier);
//                        }
//                    }
//
//                    itemShopService.getItems().add(itemStack);
//                }
//            }
//        }
//    }

    public ItemShopService findByKey(String key) {
        for (ItemShopService itemShopService : this.services) {
            if (itemShopService.getName().equalsIgnoreCase(key)) {
                return itemShopService;
            }
        }
        return null;
    }


    public List<ItemShopService> getServices() {
        return services;
    }
}
