package pl.kithard.core.border.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class BorderCommand {

    @FunnyCommand(
            name = "border",
            permission = "easyhc.commands.border",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }

        int size = Integer.parseInt(args[0]);
        World world = Bukkit.getWorld("world");
        world.getWorldBorder().setSize((size * 2 + 1));

        TextUtil.message(player, "&8(&3&l!&8) &7Ustawiono &bborder swiata &7na &f" + size);
    }

}
