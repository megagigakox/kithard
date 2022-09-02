package pl.kithard.core.shop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.shop.gui.ShopGui;

@FunnyComponent
public class ShopCommand {

    private final CorePlugin plugin;

    public ShopCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "shop",
            aliases = "sklep",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {

        new ShopGui(plugin).open(player);

    }
}
