package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

@FunnyComponent
public class GuildCommand {

    @FunnyCommand(
            name = "g",
            aliases = {"guild", "guilds", "gildie", "gildia"},
            acceptsExceeded = true
    )
    public void handle(Player player) {
        TextUtil.message(player, Arrays.asList(
                "     &8&l&m--[&b&l&m---&b&l GILDIE &b&l&m---&8&l&m]--",
                "&8» &f/g zaloz (tag) (nazwa) &8- &7Zaklada gildie.",
                "&8» &f/g usun &8- &7Usuwa gildie.",
                "&8» &f/g itemy &8- &7Sprawdz itemy na gildie.",
                "&8» &f/g info (tag) &8- &7Wyswietla informacje o gildii.",
                "&8» &f/g sojusz (tag) &8- &7Zaproszenie lub zerwanie sojuszu.",
                "&8» &f/g zapros (gracz) &8- &7Zaprasza gracza do gildii.",
                "&8» &f/g zaprosall &8- &7Zaprasza wszystkich graczy w obszarze 15 kratek.",
                "&8» &f/g wyrzuc (gracz) &8- &7Wyrzuca czlonka z gildii.",
                "&8» &f/g lider (gracz) &8- &7Przekazuje lidera gildii.",
                "&8» &f/g zastepca (gracz) &8- &7Nadaje zastepce gildii.",
                "&8» &f/g powieksz &8- &7Powieksza gildie.",
                "&8» &f/g przedluz &8- &7Przedluza waznosc gildii.",
                "&8» &f/g pvp &8- &7Zmienia tryb pvp w gildii.",
                "&8» &f/g pvpsojusz &8- &7Zmienia tryb pvp w sojuszu.",
                "&8» &f/g opusc &8- &7Opuszcza gildie.",
                "&8» &f/g magazyn &8- &7Otwiera magazyn gildyjny.",
                "&8» &f/g panel &8- &7Otwiera panel gildyjny.",
                "&8» &f/g wolnemiejsce &8- &7Znajduje wolny teren.",
                "&8» &f/g alert (wiadomosc) &8- &7Wiadomosc do czlonkow gildii"
        ));
    }
}
