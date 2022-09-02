package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class ClearCommand {

    private final CorePlugin plugin;

    public ClearCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "clear",
            playerOnly = true,
            acceptsExceeded = true,
            aliases = "ci",
            permission = "kithard.commands.clear",
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {

        if (args.length < 1) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
            TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie &fwyczyszczono &7ekwipunek!");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        target.getInventory().setArmorContents(null);
        target.getInventory().clear();
        TextUtil.message(player, "&8[&3&l!&8] &7Pomyslnie &fwyczyszczono &7ekwipunek graczowi: &b" + target.getName() + "&7!");
    }

}
