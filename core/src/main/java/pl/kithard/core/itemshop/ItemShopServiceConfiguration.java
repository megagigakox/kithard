package pl.kithard.core.itemshop;

import eu.okaeri.configs.OkaeriConfig;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.ItemStackBuilder;

import java.util.*;

public class ItemShopServiceConfiguration extends OkaeriConfig {

    private List<ItemShopService> services = new ArrayList<>();

    public ItemShopServiceConfiguration() {
        this.services.add(new ItemShopService(
                "vip",
                false,
                Collections.singletonList("lp user {PLAYER} parent set vip"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &eVIP &7na zawsze!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "vipedycja",
                false,
                Collections.singletonList("lp user {PLAYER} parent addtemp vip 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &eVIP &7na edycje!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "svip",
                false,
                Collections.singletonList("lp user {PLAYER} parent set svip"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &6SVIP &7na zawsze!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "svipedycja",
                false,
                Collections.singletonList("lp user {PLAYER} parent addtemp svip 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &6SVIP &7na edycje!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "sponsor",
                false,
                Collections.singletonList("lp user {PLAYER} parent set sponsor"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &bSponsor &7na zawsze!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "sponsoredycja",
                false,
                Collections.singletonList("lp user {PLAYER} parent addtemp sponsor 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &bSponsor &7na edycje!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "hard",
                false,
                Collections.singletonList("lp user {PLAYER} parent set hard"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &3HARD &7na zawsze!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "hardedycja",
                false,
                Collections.singletonList("lp user {PLAYER} parent addtemp hard 21d"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil range &3HARD &7na edycje!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "case",
                true,
                Collections.singletonList("chestgive {PLAYER} {AMOUNT}"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l{AMOUNT}x Magiczne Skrzynki!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "key",
                true,
                Collections.singletonList("crates give physical basic {AMOUNT} {PLAYER}"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l{AMOUNT}x Klucze do legendarnej skrzyni!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "lom",
                true,
                Collections.singletonList("lom {PLAYER} {AMOUNT}"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&l{AMOUNT}x łomy do otworzenia sejfu!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "unban",
                false,
                Collections.singletonList("unban {PLAYER}"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&lunbana!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));

        this.services.add(new ItemShopService(
                "unbanip",
                false,
                Collections.singletonList("unbanip {PLAYER}"),
                Arrays.asList(
                        "",
                        "       &8&l&m--[&b&l&m---&b&l ITEMSHOP &b&l&m---&8&l&m]--",
                        " &7Gracz &f{PLAYER} &7zakupil &b&lunbana ip!",
                        " &7Dziekujemy za &3wsparcie &7naszego serwera!",
                        " &7Nasz itemshop serwerowy&8: &fhttps://kithard.pl/",
                        ""
                ),
                new ArrayList<>()));
    }


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
