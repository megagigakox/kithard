package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.LocationUtil;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunnyComponent
public class GuildInviteAllCommand {

    private final CorePlugin plugin;

    public GuildInviteAllCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zaprosall",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {

        List<Player> nearbyPlayers = new ArrayList<>();
        for (Player it : Bukkit.getOnlinePlayers()) {
            if (LocationUtil.distance(it.getLocation(), player.getLocation()) >= 15) {
                continue;
            }

            if (player.equals(it)) {
                continue;
            }

            Guild targetGuild = this.plugin.getGuildCache().findByPlayer(it);
            if (targetGuild != null) {
                continue;
            }

            TextUtil.message(it, " &7Zostales zaproszony do gildii &b[" + guild.getTag() + "] &3" + guild.getName() + " &7przez &f" + player.getName() + "&7.");
            TextUtil.message(it, " &7Wpisz &b/g dolacz " + guild.getTag() + " &7aby dolaczyc do gildii.");
            nearbyPlayers.add(it);
        }

        if (nearbyPlayers.isEmpty()) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie ma nikogo w poblizu!");
            return;
        }

        nearbyPlayers.forEach(it -> guild.getMemberInvites().add(it.getUniqueId()));
        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie zaproszono &2" + nearbyPlayers.size() + " &agraczy w poblizu do gildii.");
    }
}
