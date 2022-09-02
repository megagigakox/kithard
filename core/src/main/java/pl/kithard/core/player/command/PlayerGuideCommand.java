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
                "&8» &e/vip&8, &6/svip&8, &b/mvp&8, &5/legenda &8- &7Rangi premium",
                ""

        ).forEach(it -> TextUtil.message(player, it));

    }

}
