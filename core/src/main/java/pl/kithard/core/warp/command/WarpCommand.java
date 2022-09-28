package pl.kithard.core.warp.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.kithard.core.warp.gui.WarpGui;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.warp.Warp;

@FunnyComponent
public class WarpCommand {

    private final CorePlugin plugin;

    public WarpCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "warp",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void warp(Player player, CorePlayer corePlayer, String[] args) {
        WarpGui warpGui = new WarpGui(plugin);
        if (args.length < 1) {
            warpGui.open(player);
            return;
        }

        Warp warp = this.plugin.getWarpCache().findByName(args[0]);
        if (warp == null) {
            warpGui.open(player);
            return;
        }

        corePlayer.teleport(warp.getLocation(), 10);
    }

    @FunnyCommand(
            name = "createwarp",
            permission = "kithard.commands.createwarp",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void createWarp(Player player, String[] args) {

        if (args.length < 2) {
            TextUtil.correctUsage(player, "/createwarp (nazwa) (ikonka)");
            return;
        }

        this.plugin.getWarpFactory().create(args[0], player.getLocation(), Material.valueOf(args[1]));
        TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &astworzono &7warp o nazwie: &b" + args[0]);
    }

    @FunnyCommand(
            name = "deletewarp",
            permission = "kithard.commands.deletewarp",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void deleteWarp(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/deletewarp (nazwa)");
            return;
        }

        Warp warp = this.plugin.getWarpCache().findByName(args[0]);
        if (warp == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen warp nie istnieje!");
            return;
        }

        this.plugin.getWarpFactory().delete(warp);
        TextUtil.message(player, "&8(&3&l!&8) &7Pomyslnie &cusunieto &7warp o nazwie: &b" + args[0]);
    }

}
