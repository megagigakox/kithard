package pl.kithard.core.configuration.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;
import pl.kithard.core.CorePlugin;

@FunnyComponent
public class ReloadConfigurationCommand {

    private final CorePlugin plugin;

    public ReloadConfigurationCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "reloadconfig",
            permission = "kithard.commands.reloadconfig",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            this.plugin.getDropItemConfiguration().load();
            this.plugin.getDepositItemConfiguration().load();
            this.plugin.getShopConfiguration().load();
            this.plugin.getItemShopServiceConfiguration().load();
            this.plugin.getCustomEnchantConfiguration().load();
        });
    }

}
