package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class RepairPickaxeCommand {

    private final CorePlugin plugin;

    public RepairPickaxeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "naprawkilof",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player) {

        if (!player.getItemInHand().getType().toString().contains("PICKAXE")) {
            TextUtil.message(player, "&8(&4&l!&8) &cMusisz trzymac kilof w reku aby go naprawic!");
            return;
        }

        if (player.getLevel() < 30) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz 30lvl aby naprawic kilof!");
            return;
        }

        player.getItemInHand().setDurability((short) 0);
        player.setLevel(player.getLevel() - 30);
        TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie naprawiono kilof.");
    }

}
