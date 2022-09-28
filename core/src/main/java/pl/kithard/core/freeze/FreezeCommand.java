package pl.kithard.core.freeze;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class FreezeCommand {

    private final CorePlugin plugin;

    public FreezeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "freeze",
            permission = "kithard.commands.freeze",
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }

        long time = TimeUtil.timeFromString(args[0]) + System.currentTimeMillis();
        ServerSettings serverSettings = this.plugin.getServerSettings();
        serverSettings.setFreeze(time);
        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getServerSettingsService().save(serverSettings));
        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie ustawiono zamrozenie na: &2" + TimeUtil.formatTimeMillis(time - System.currentTimeMillis()));

    }
}
