package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;

@FunnyComponent
public class GuildHomeTeleportCommand {

    @FunnyCommand(
            name = "g home",
            aliases = {"g baza", "g dom"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(CorePlayer player, Guild guild) {
        player.teleport(guild.getHome(), 10);
    }

}
