package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@FunnyComponent
public class GuildInfoCommand {

    private final CorePlugin plugin;

    public GuildInfoCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g info",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {

        Guild guild;
        if (args.length == 0) {
            guild = this.plugin.getGuildCache().findByPlayer(player);
        } else {
            guild = this.plugin.getGuildCache().findByTag(args[0]);
        }

        if (guild == null && args.length == 0) {
            TextUtil.correctUsage(player, "/g info (tag)");
            return;
        }

        if (guild == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cGildia o takim tagu &4nie istnieje&c!");
            return;
        }

        TextUtil.message(player, "     &8&l&m--[&b&l&m---&b&l " + guild.getTag() + " &b&l&m---&8&l&m]--");
        TextUtil.message(player, guildInfo(guild, plugin));
    }

    public static List<String> guildInfo(Guild guild, CorePlugin plugin) {
        List<String> info = new ArrayList<>();
        int size = guild.getRegion().getSize() * 2;

        info.add("&8» &7Nazwa: &f" + guild.getName());
        info.add("&8» &7Lider: &f" + plugin.getCorePlayerCache().findByUuid(guild.getOwner()).getName());

        String deputies = guild.getDeputies().isEmpty()
                ? "&fBrak."
                : guild.getDeputies()
                .stream()
                .map(GuildMember::getName)
                .collect(Collectors.joining("&8,&f "));

        info.add("&8» &7Zastepcy: &f" + deputies + " &8(&b" + guild.getDeputies().size() + "&8/&b3&8)");
        info.add("&8» &7Punkty: &f" + guild.getPoints());
        info.add("&8» &7Ranking: &f" );
        info.add("&8» &7KD-Ratio: &f" + guild.getKd());
        info.add("&8» &7Zabojstwa: &f" + guild.getKills());
        info.add("&8» &7Smierci: &f" + guild.getDeaths());
        info.add("&8» &7Rozmiar: &f" + size + "x" + size);
        info.add("&8» &7Utworzona: &f" + TimeUtil.formatTimeMillisToDate(guild.getCreateDate()));
        info.add("&8» &7Wazna do: &f" + TimeUtil.formatTimeMillisToDate(guild.getExpireDate()));
        info.add("&8» &7Wygasa za: &f" + TimeUtil.formatTimeMillis(guild.getExpireDate() - System.currentTimeMillis()));
        info.add("&8» &7Atak mozliwy za: &f" +
                (guild.getLastAttackDate() > System.currentTimeMillis()
                        ? TimeUtil.formatTimeMillis(guild.getLastAttackDate() - System.currentTimeMillis())
                        : "&aw tym momencie!"));
        info.add("&8» &7Zycia gildii: &c" + guild.getHearts());

        String allies = guild.getAllies().isEmpty()
                ? "&fBrak."
                : String.join("&8,&f ", guild.getAllies());

        info.add("&8» &7Sojusze: &f" + allies + " &8(&b" + guild.getAllies().size() + "&8/&b3&8)");

        Set<GuildMember> onlineMembers =
                guild.getMembers().stream()
                        .filter(guildMember -> plugin.getServer().getPlayer(guildMember.getUuid()) != null)
                        .collect(Collectors.toSet());

        String members = guild.getMembers().isEmpty()
                ? "&fBrak."
                : guild.getMembers()
                .stream()
                .map(guildMember -> (onlineMembers.contains(guildMember) ? "&a" : "&c") + guildMember.getName())
                .collect(Collectors.joining("&8, "));

        info.add("&8» &7Czlonkowie: " + members + " &8(&a" + onlineMembers.size() + "&8/&7" + guild.getMembers().size() + "&8)");
        return info;
    }

}
