package pl.kithard.core.guild.command.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

@FunnyComponent
public class GuildAdminCommand {

    @FunnyCommand(
            name = "ga",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender) {

        TextUtil.message(sender, Arrays.asList(
                "   &8&m--&r&8&l[&b&m-----&r&8&l[&r&8&m---&3&l GILDIE ADMIN &r&8&m---&r&8&l]&r&b&m-----&r&8&l]&r&8&m--",
                "&8» &b/ga tp (tag) &8- &7Teleportuje do gildii",
                "&8» &b/ga usun (tag) &8- &7Usuwa gildie",
                "&8» &b/ga itemy (gracz) &8- &7Nadaj itemy na gildie",
                "&8» &b/ga lider (tag) (gracz) &8- &7Wyswietla informacje o gildii",
                "&8» &b/ga dolacz (tag) (gracz) &8- &7Zaproszenie lub zerwanie sojuszu",
                "&8» &b/ga skarbiec (tag) &8- &7Zaprasza gracza do gildii"));


    }

}
