package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class SpawnCommand {

    private final CorePlugin plugin;

    public SpawnCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "spawn",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        Location spawnLocation = Bukkit.getWorld("world")
                .getSpawnLocation()
                .add(0.5, 0.5, 0.5);

        if (args.length == 1 && player.hasPermission("kithard.commands.spawn.other")) {
            Player target = this.plugin.getServer().getPlayerExact(args[0]);
            if (target == null) {
                return;
            }

            target.teleport(spawnLocation);
            TextUtil.message(player, "&aPrzeteleportowano!");
        }

        if (player.getWorld().getName().equals("gtp")) {
            corePlayer.teleport(spawnLocation, 3);
            return;
        }

        corePlayer.teleport(spawnLocation, 10);

    }

}
