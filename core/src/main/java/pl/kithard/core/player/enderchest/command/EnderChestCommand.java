package pl.kithard.core.player.enderchest.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.enderchest.gui.EnderChestGui;

@FunnyComponent
public class EnderChestCommand {

    private final CorePlugin plugin;

    public EnderChestCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "enderchest",
            aliases = {"ec"},
            acceptsExceeded = true,
            playerOnly = true,
            permission = "kithard.commands.enderchest"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            new EnderChestGui(plugin).open(player);
        }
        else if (player.hasPermission("kithard.commands.enderchest.other")) {
            Player other = this.plugin.getServer().getPlayer(args[0]);
            if (other == null) {
                return;
            }

            new EnderChestGui(plugin).open(other);
        }
    }
}
