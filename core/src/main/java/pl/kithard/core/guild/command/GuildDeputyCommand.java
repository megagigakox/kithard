package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildDeputyCommand {

    private final CorePlugin plugin;

    public GuildDeputyCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zastepca",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, Guild guild, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g zastepca (gracz)");
            return;
        }

        if (!guild.isOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie jestes zalozycielem gildii!");
            return;
        }

        GuildMember guildMember = guild.findMemberByName(args[0]);
        if (guildMember == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz nie jest w twojej gildii!");
            return;
        }

        if (guild.getDeputies().contains(guildMember.getUuid())) {
            guild.getDeputies().remove(guildMember.getUuid());
            TextUtil.message(player, "&8(&3&l!&8) &7Gracz &f" + args[0] + " &7nie posiada od teraz zastepcy!");
            return;
        }

        if (guild.getDeputies().size() == 3) {
            TextUtil.message(player, "&8(&4&l!&8) &cTwoja gildia posiada juz maksymalna ilosc zastepcow!");
            return;
        }

        guild.getDeputies().add(guildMember.getUuid());
        guild.setNeedSave(true);
        TextUtil.message(player, "&8(&4&l!&8) &cPamietaj że nadanie zastepcy powoduje nadanie dostepu do &4wszystkich &cuprawnien gildyjnych!");
        TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &bnadales &7zastepce czlonkowi &f" + args[0]);
    }
}
