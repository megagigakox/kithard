package pl.kithard.core.guild.command.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.GuildMember;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.InventoryUtil;
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
                "&8» &b/ga itemy (gracz) &8- &7Nadaj itemy na gildie",
                "&8» &b/ga owner (tag) (gracz) &8- &7Nadaje ownera",
                "&8» &b/ga dolacz (tag) (gracz) &8- &7Dolacza do gildii",
                "&8» &b/ga zycia (tag) (ilosc) &8- &7Ustawia dana ilosc zyc gildii",
                "&8» &b/ga hp (tag) (ilosc) &8- &7Ustawia dana ilosc hp gildii",
                "&8» &b/ga set (tag) (points/deaths/kills (ilosc) &8- &7Ustawianie statystk gildii"
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
                guild2.removeMember(guild2.findMemberByUuid(corePlayer.getUuid()));
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
                guild2.removeMember(guild2.findMemberByUuid(corePlayer.getUuid()));
            }
        }

        guild.addMember(new GuildMember(guild.getTag(), corePlayer.getUuid(), corePlayer.getName()));
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

        guild.removeMember(member);
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aWyrzucono gracza: &b" + member.getName());
    }

    @FunnyCommand(
            name = "ga zycia",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleLives(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        int lives = Integer.parseInt(args[1]);
        guild.setLives(lives);
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aUstawiono: &b" + lives + " &ażyć");
    }

    @FunnyCommand(
            name = "ga hp",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleHp(CommandSender sender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        int hp = Integer.parseInt(args[1]);
        guild.setHp(hp);
        guild.setNeedSave(true);
        TextUtil.message(sender, "&aUstawiono: &b" + hp + " &ahp");
    }

    @FunnyCommand(
            name = "ga itemy",
            permission = "kithard.commands.guildadmin.items",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handleItems(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/ga itemy (gracz)");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            return;
        }

        InventoryUtil.addItem(player, CoreConstants.PLAYER_GUILD_ITEMS);
        TextUtil.message(player, "&aNadano itemy na gildie graczowi: &b" + target.getName());

    }

    @FunnyCommand(
            name = "ga tp",
            acceptsExceeded = true,
            playerOnly = true,
            permission = "kithard.commands.guildadmin.tp"
    )
    public void handleTp(Player player, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/ga tp (tag)");
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            return;
        }

        player.teleport(guild.getRegion().getCenter().clone().add(0, 100, 0));
        TextUtil.message(player, "&aPomyslnie przeteleportowano na teren gildii: &b[" + guild.getTag() + "]");
    }

    @FunnyCommand(
            name = "ga set",
            permission = "kithard.commands.guildadmin",
            acceptsExceeded = true
    )
    public void handleSet(CommandSender sender, String[] args) {
        if (args.length < 3) {
            return;
        }

        Guild guild = this.plugin.getGuildCache().findByTag(args[0]);
        if (guild == null) {
            TextUtil.message(sender, "guild null");
            return;
        }

        int value = Integer.parseInt(args[2]);
        switch (args[1]) {

            case "points": {

                guild.setPoints(value);
                guild.setNeedSave(true);
                TextUtil.message(sender, "&aUstawiono: &b" + value + " &apunktow");
                return;
            }

            case "kills": {

                guild.setKills(value);
                guild.setNeedSave(true);
                TextUtil.message(sender, "&aUstawiono: &b" + value + " &bkilli");
                return;
            }

            case "deaths": {

                guild.setDeaths(value);
                guild.setNeedSave(true);
                TextUtil.message(sender, "&aUstawiono: &b" + value + " &bdeadow");
            }

        }

    }

}
