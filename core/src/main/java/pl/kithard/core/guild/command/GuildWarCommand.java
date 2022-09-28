package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class GuildWarCommand {

    @FunnyCommand(
            name = "g walka",
            aliases = "g war",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (!guild.isDeputyOrOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8(&4&l!&8) &cTylko lider lub zastepca moze tego uzyc!");
            return;
        }

        if (guild.getGuildWarCooldown() > System.currentTimeMillis()) {
            TextUtil.message(player, "&8(&4&l!&8) &cTa komende mozesz uzyc dopiero za: &4" + TimeUtil.formatTimeMillis(guild.getGuildWarCooldown() - System.currentTimeMillis()));
            return;
        }

        Bukkit.broadcastMessage(TextUtil.color("       &8&l&m--[&b&l&m---&b&l WALKA &b&l&m---&8&l&m]--"));
        Bukkit.broadcastMessage(TextUtil.color("         &7Gildia&8: [&3&l" + guild.getTag() + "&8] &b" + guild.getName()));
        Bukkit.broadcastMessage(TextUtil.color(" &7Zaprasza na cuboid ktory zanjduje sie na kordach&8:"));
        Bukkit.broadcastMessage(TextUtil.color("              &7(&bX&8: " + guild.getRegion().getX() + "&8, &bZ&8: &b" + guild.getRegion().getZ() + "&7)"));
        guild.setGuildWarCooldown(TimeUnit.MINUTES.toMillis(5) + System.currentTimeMillis());
    }

}
