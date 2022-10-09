package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunnyComponent
public class GuildInviteAll {

    private final CorePlugin plugin;

    public GuildInviteAll(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g zaprosall",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {


    }
}
