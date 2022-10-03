package pl.kithard.core.player.enderchest.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

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
    public void handle(CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            corePlayer.getEnderChest().openInventory(corePlayer.source(), corePlayer.source());
        }
        else if (corePlayer.source().hasPermission("kithard.commands.enderchest.other")) {
            CorePlayer target = this.plugin.getCorePlayerCache().findByName(args[0]);
            if (target == null) {
                return;
            }

            if (target.source() == null) {
                return;
            }

            target.getEnderChest().openInventory(target.source(), corePlayer.source());
        }
    }
}
