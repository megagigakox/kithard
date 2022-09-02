package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class SetSpawnCommand {

    @FunnyCommand(
            name = "setspawn",
            playerOnly = true,
            permission = "kithard.commands.setspawn",
            acceptsExceeded = true
    )
    public void handle(Player player) {
        player.getWorld().setSpawnLocation(
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ());

        TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie &fustawiono &7nowa lokalizacje spawna!");
    }

}
