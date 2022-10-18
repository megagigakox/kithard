package pl.kithard.core.grouptp;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GroupTeleportCommand {

    private final CorePlugin plugin;

    public GroupTeleportCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "gtptest",
            permission = "gtptest",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        if (Bukkit.getWorld("gtp") != null) {
            return;
        }

        this.plugin.getGroupTeleportCache().prepareWorld();
        TextUtil.message(player, "stworzono swiat teparkowy");
    }

}
