package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class SaveAllCommand {

    private final CorePlugin plugin;

    public SaveAllCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "savealltodb",
            permission = "kithard.commands.savealltodb",
            acceptsExceeded = true
    )
    public void handle(CommandSender commandSender) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {

            this.plugin.getGuildFactory().saveAll(false);
            this.plugin.getCorePlayerFactory().saveAll(false);

        });

    }

}
