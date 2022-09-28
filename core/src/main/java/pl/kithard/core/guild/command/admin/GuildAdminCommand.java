package pl.kithard.core.guild.command.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;

@FunnyComponent
public class GuildAdminCommand {

    private final CorePlugin plugin;

    public GuildAdminCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

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
                "&8» &b/ga owner (tag) (gracz) &8- &7Nadaje ownera",
                "&8» &b/ga dolacz (tag) (gracz) &8- &7Dolacza do gildii",
                "&8» &b/ga skarbiec (tag) &8- &7Otwiera skarbiec gildii"
        ));
    }

    @FunnyCommand(
            name = "ga owner",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleOwner(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[1]);
        if (corePlayer == null) {
            TextUtil.message(sender, "null player");
            return;
        }

        Guild guild2 = this.plugin.getGuildCache().findByPlayerName(corePlayer.getName());
        if (guild2 != null) {
            if (guild2.getOwner().equals(corePlayer.getUuid())) {
                this.plugin.getGuildFactory().delete(guild2);
            }
            else {
                guild2.getMembers().remove(guild2.findMemberByUuid(corePlayer.getUuid()));
            }
        }

        guild.setOwner(corePlayer.getUuid());
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aUstawiono nowego lidera gildii: &b" + corePlayer.getName());
    }

    @FunnyCommand(
            name = "ga dolacz",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleJoin(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[1]);
        if (corePlayer == null) {
            TextUtil.message(sender, "null player");
            return;
        }

        Guild guild2 = this.plugin.getGuildCache().findByPlayerName(corePlayer.getName());
        if (guild2 != null) {
            if (guild2.getOwner().equals(corePlayer.getUuid())) {
                this.plugin.getGuildFactory().delete(guild2);
            }
            else {
                guild2.getMembers().remove(guild2.findMemberByUuid(corePlayer.getUuid()));
            }
        }

        guild.getMembers().add(new GuildMember(guild.getTag(), corePlayer.getUuid(), corePlayer.getName()));
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aDodano gracza: &b" + corePlayer.getName());
    }

    @FunnyCommand(
            name = "ga wyrzuc",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleKick(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        GuildMember member = guild.findMemberByName(args[1]);
        if (member == null) {
            TextUtil.message(sender, "member == null");
            return;
        }

        guild.getMembers().remove(member);
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aWyrzucono gracza: &b" + member.getName());
    }

}
