package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildAllyInviteCommand {

    private final CorePlugin plugin;

    public GuildAllyInviteCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g sojusz",
            aliases = "g ally",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild, String[] args) {
        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.ALLY_CONCLUDING)) {
            return;
        }

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g sojusz (tag)");
            return;
        }

        Guild otherGuild = this.plugin.getGuildCache().findByTag(args[0]);

        if (otherGuild == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cGildia o tym tagu nie istnieje!");
            return;
        }

        if (guild.getTag().equalsIgnoreCase(otherGuild.getTag())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie możesz zawrzec sojuszu ze swoja gildia!");
            return;
        }

        if (guild.getAllies().contains(otherGuild.getTag())) {
            guild.getAllies().remove(otherGuild.getTag());
            otherGuild.getAllies().remove(guild.getTag());

            guild.setNeedSave(true);
            otherGuild.setNeedSave(true);

            guild.addLog(new GuildLog(
                    GuildLogType.ALLY_BREAK,
                    "&f" + player.getName() + " &7zerwal sojusz z gildia: &8[" + otherGuild.getTag() + "&8]")
            );

            otherGuild.addLog(new GuildLog(
                    GuildLogType.ALLY_BREAK,
                    "&f" + player.getName() + " &8[&c" + guild.getTag() +"&8] &7zerwal sojusz z wasza gildia")
            );

            Bukkit.broadcastMessage(TextUtil.color("&8[&3&l!&8] &7Gildia &8[&b" + guild.getTag() + "&8] &7zerwala sojusz z gildia &8[&b" + otherGuild.getTag() + "&8]"));
            return;
        }

        if (guild.getAllyInvites().contains(otherGuild.getTag())) {
            guild.getAllyInvites().remove(otherGuild.getTag());

            TextUtil.message(player, "&8[&4&l!&8] &cWycofales zaproszenie do sojuszu!");
            return;
        }

        if (guild.getAllies().size() == 3) {
            TextUtil.message(player, "&8[&4&l!&8] &cTwoja gildia posiada maksymalna liczbe sojuszy!");
            return;
        }

        if (otherGuild.getAllies().size() == 3) {
            TextUtil.message(player, "&8[&4&l!&8] &cGildia z ktora chcesz zawrzec sojusz posiada maksymalna liczbe sojuszy!");
            return;
        }

        if (!otherGuild.getAllyInvites().contains(guild.getTag())) {
            guild.getAllyInvites().add(otherGuild.getTag());
            TextUtil.message(player, "&8[&3&l!&8] &7Wyslales &3zaproszenie &7do sojuszu z gildia &8[&b" + otherGuild.getTag() + "&8]&7!");

            for (GuildMember member : otherGuild.getMembers()) {
                Player memberPlayer = this.plugin.getServer().getPlayer(member.getUuid());
                if (memberPlayer != null) {

                    TextUtil.message(memberPlayer, " &7Twoja gildia otrzymala zaproszenie o sojusz z gildia &8[&b" + guild.getTag() + "&8]&7!");
                    TextUtil.message(memberPlayer, " &7Wpisz &b/g sojusz " + guild.getTag() + "&7, aby zaakceptowac!");

                }
            }
            return;
        }

        guild.getAllyInvites().remove(otherGuild.getTag());
        otherGuild.getAllyInvites().remove(guild.getTag());

        guild.getAllies().add(otherGuild.getTag());
        otherGuild.getAllies().add(guild.getTag());

        otherGuild.setNeedSave(true);
        guild.setNeedSave(true);

        guild.addLog(new GuildLog(
                GuildLogType.ALLY_INCLUDE,
                "&f" + player.getName() + " &7zawarl sojusz z gildia: &8[" + otherGuild.getTag() + "&8]")
        );

        otherGuild.addLog(new GuildLog(
                GuildLogType.ALLY_INCLUDE,
                "&f" + player.getName() + " &8[&c" + guild.getTag() +"&8] &7zawarl sojusz z wasza gildia")
        );

        Bukkit.broadcastMessage(TextUtil.color("&8[&3&l!&8] &7Gildia &8[&b" + guild.getTag() + "&8] &7zawarla sojusz z gildia &8[&b" + otherGuild.getTag() + "&8]"));
    }
}
