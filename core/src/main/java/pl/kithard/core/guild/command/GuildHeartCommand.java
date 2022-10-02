package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;

@FunnyComponent
public class GuildHeartCommand {

    @FunnyCommand(
            name = "g serce",
            acceptsExceeded = true
    )
    public void handle(CorePlayer corePlayer, Guild guild) {

        corePlayer.teleport(guild.getRegion().getCenter().clone(), 10);

    }
}
