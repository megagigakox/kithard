package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

@FunnyComponent
public class PlayerGuideCommand {

    @FunnyCommand(
            name = "pomoc",
            aliases = {"help"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {


        Arrays.asList(
                "     &8&l&m--[&b&l&m---&b&l POMOC &b&l&m---&8&l&m]--",
                "&8» &f/drop &8- &7Drop z kamienia, cx oraz skrzynek premium.",
                "&8» &f/g &8- &7Komendy gildyjne.",
                "&8» &f/craftingi &8- &7Lista dostepnych craftingow.",
                "&8» &f/sklep &8- &7Zarabiaj i kupuj itemy.",
                "&8» &f/schowek &8- &7Schowek z limitami.",
                "&8» &f/incognito &8- &7Ukryj swoj nick.",
                "&8» &f/efekty &8- &7Kupno efektow.",
                "&8» &f/kit &8- &7Dostepne zestawy.",
                "&8» &f/warp &8- &7Lista warpow.",
                "&8» &f/os &8- &7Zdobywaj nagrody za osiagniecia.",
                "&8» &f/topki &8- &7Sprawdz najlepszych graczy na serwerze.",
                "&8» &f/sellall &8- &7Sprzedaz wszystkich przedmiotow.",
                "&8» &f/repair &8- &7Naprawa przedmiotow.",
                "&8» &f/ustawienia &8- &7Zarzadzanie wiadomosciami na chacie itp.",
                "&8» &f/nagroda &8- &7Nagroda za dolaczenie na discorda.",
                "&8» &f/bloki &8- &7Wymiana sztabek na bloki.",
                "&8» &f/kosz &8- &7Pozbadz sie niepotrzebnych itemkow.",
                "&8» &f/gracz &8- &7Statystyki danego gracza.",
                "",
                "   &8(Rangi premium do kupna w itemshopie)",
                "    &8/&evip&8, /&6svip&8, /&bsponsor&8, /&3hard ",
                "   &8(Rangi dla youtuberow/tiktokerow)",
                "        &8/&emedia&8, /&emedia&f+",
                ""

        ).forEach(it -> TextUtil.message(player, it));

    }

    @FunnyCommand(
            name = "vip",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void vip(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&e&l&m---&e&l VIP &e&l&m---&8&l&m]--",
                        "",
                        " &7Ranga vip posiada&8:",
                        "  &8- &7Dostep do komendy&8: /&frepair",
                        "  &8- &7Dostep do enderchesta pod komenda&8: /&fec",
                        "  &8- &7Mozliwosc odbierania zestawu dla&8: &eVIP'a",
                        "  &8- &7Dostep do dodatkowego domu",
                        "       &8(&7Lacznie 2 homy&8)"
                ));
    }

    @FunnyCommand(
            name = "svip",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void svip(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&e&l&m---&6&l SVIP &e&l&m---&8&l&m]--",
                        "",
                        " &7Ranga svip posiada&8:",
                        "  &8- &7Zwiekszony drop ze stone o &e10%",
                        "  &8- &7Dostep do komendy&8: /&frepair",
                        "  &8- &7Dostep do komendy&8: /&frepair all",
                        "  &8- &7Uzupelnianie glodu pod komenda&8: /&ffeed",
                        "  &8- &7Dostep do enderchesta pod komenda&8: /&fec",
                        "  &8- &7Przenosny crafting pod komenda&8: /&fwb",
                        "  &8- &7Mozliwosc odbierania zestawow&8:",
                        "     &8- /&fkit vip",
                        "     &8- /&fkit svip",
                        "  &8- &7Powiekszony enderchest o 1 dodatkowy rząd",
                        "  &8- &7Dostep do dodatkowego domu",
                        "       &8(&7Lacznie 3 domki&8)"
                ));
    }

    @FunnyCommand(
            name = "sponsor",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void sponsor(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&b&l&m---&b&l SPONSOR &b&l&m---&8&l&m]--",
                        "",
                        " &7Ranga sponsor posiada&8:",
                        "  &8- &7Zmniejszone itemy na gildie o &e50%",
                        "      &8(^ Na starcie edycji nie dziala ^)",
                        "  &8- &7Zwiekszony drop ze stone o &e15%",
                        "  &8- &7Dostep do komendy&8: /&frepair",
                        "  &8- &7Dostep do komendy&8: /&frepair all",
                        "  &8- &7Uzupelnianie glodu pod komenda&8: /&ffeed",
                        "  &8- &7Uzdrowienie pod komenda&8: /&fheal",
                        "  &8- &7Dostep do enderchesta pod komenda&8: /&fec",
                        "  &8- &7Przenosny crafting pod komenda&8: /&fwb",
                        "  &8- &7Mozliwosc odbierania zestawow&8:",
                        "     &8- /&fkit vip",
                        "     &8- /&fkit svip",
                        "     &8- /&fkit sponsor",
                        "     &8- /&fkit magiczna_skrzynka",
                        "  &8- &7Powiekszony enderchest o 2 dodatkowe rzędy",
                        "  &8- &7Dostep do dodatkowego domu",
                        "       &8(&7Lacznie 4 domki&8)"
                ));
    }

    @FunnyCommand(
            name = "hard",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void hard(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&3&l&m---&3&l HARD &3&l&m---&8&l&m]--",
                        "",
                        " &7Ranga hard posiada&8:",
                        "  &8- &7Zmniejszone itemy na gildie o &e50%",
                        "      &8(^ Na starcie edycji nie dziala ^)",
                        "  &8- &7Zwiekszony drop ze stone o &e20%",
                        "  &8- &7Dostep do komendy&8: /&frepair",
                        "  &8- &7Dostep do komendy&8: /&frepair all",
                        "  &8- &7Darmowy &b&lT&3&lu&f&lr&b&lb&3&lo&f&lD&b&lr&3&lo&f&lp &7na 10min co godzine&8: /&ffreeturbo",
                        "  &8- &7Uzupelnianie glodu pod komenda&8: /&ffeed",
                        "  &8- &7Dostep do enderchesta pod komenda&8: /&fec",
                        "  &8- &7Uzdrowienie pod komenda&8: /&fheal",
                        "  &8- &7Przenosny crafting pod komenda&8: /&fwb",
                        "  &8- &7Przenosny enchant pod komenda&8: /&fenchant",
                        "  &8- &7Mozliwosc odbierania zestawow&8:",
                        "     &8- /&fkit vip",
                        "     &8- /&fkit svip",
                        "     &8- /&fkit sponsor",
                        "     &8- /&fkit hard",
                        "     &8- /&fkit magiczna skrzynka",
                        "  &8- &7Powiekszony enderchest o 3 dodatkowe rzędy",
                        "  &8- &7Dostep do dodatkowego domu",
                        "       &8(&7Lacznie 5 domkow&8)"
                ));
    }

    @FunnyCommand(
            name = "media",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void media(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&e&l&m---&6&l MEDIA &e&l&m---&8&l&m]--",
                        "",
                        " &7Ranga media posiada&8: &7to samo co ranga &6svip",
                        "                  &8(/svip)",
                        " &7Range mozesz otrzymac gdy posiadasz&8:",
                        "  &8- &7Minimum &f200 subskrybcji &7na yt",
                        "  &8- &7Minimum &f500 obserwujacych &7na tiktoku",
                        " &7Aby otrzymac range stworz &3ticketa",
                        " &7Na naszym discordzie serwerowym&8: &fdc.kithard.pl"
                ));
    }

    @FunnyCommand(
            name = "media+",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void mediaplus(Player player) {
        TextUtil.message(player,
                Arrays.asList(
                        "     &8&l&m--[&e&l&m---&6&l MEDIA&f&l+ &e&l&m---&8&l&m]--",
                        "",
                        " &7Ranga media+ posiada&8: &7to samo co ranga &3HARD",
                        "                  &8(/hard)",
                        " &7Range mozesz otrzymac gdy posiadasz&8:",
                        "  &8- &7Minimum &f1000 subskrybcji &7na yt",
                        "  &8- &7Minimum &f2000 obserwujacych &7na tiktoku",
                        " &7Aby otrzymac range stworz &3ticketa",
                        " &7Na naszym discordzie serwerowym&8: &fdc.kithard.pl"
                ));
    }

}
