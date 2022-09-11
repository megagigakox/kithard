package pl.kithard.core.shop.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.shop.gui.ShopGui;
import pl.kithard.core.util.TextUtil;

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

    @FunnyCommand(
            name = "resethajs",
            permission = "kithard.core.wazne"
    )
    public void handlex(Player player) {
        for (CorePlayer corePlayer : this.plugin.getCorePlayerCache().getValues()) {
            if (corePlayer.getMoney() >= 1200){
                corePlayer.setMoney(corePlayer.getMoney() - 1200);
            }
        }
    }
}
