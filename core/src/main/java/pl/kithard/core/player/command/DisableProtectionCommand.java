package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class DisableProtectionCommand {

    private final CorePlugin plugin;

    public DisableProtectionCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "wylaczochrone",
            aliases = "disableprotection",
            acceptsExceeded = true
    )
    public void handle(Player player, CorePlayer corePlayer) {
        if (corePlayer.getProtection() <= System.currentTimeMillis()) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie masz ochrony!");
            return;
        }

        corePlayer.setProtection(0);
        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie wylaczono ochrone!");
    }
}
