package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class FreeTurboCommand {

    private final CorePlugin plugin;

    public FreeTurboCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "freeturbo",
            acceptsExceeded = true,
            permission = "kithard.commands.freeturbo"
    )
    public void handle(Player player, CorePlayer corePlayer) {
        if (corePlayer.getCooldown().getFreeTurboCooldown() > System.currentTimeMillis()) {
            TextUtil.message(player, "&8(&4&l!&8) &cFreeturbo mozesz odebrac kolejny raz za: &4" + TimeUtil.formatTimeMillis(corePlayer.getCooldown().getFreeTurboCooldown() - System.currentTimeMillis()));
            return;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "turbo " + player.getName() + " 10m");
        corePlayer.getCooldown().setFreeTurboCooldown(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
    }
}
