package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FunnyComponent
public class TeleportAcceptCommand {

    private final CorePlugin plugin;

    public TeleportAcceptCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "tpaccept",
            playerOnly = true,
            acceptsExceeded = true,
            completer = "online-players:5"
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/tpaccept (gracz/all)");
            return;
        }

        if (args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("all")) {

            List<UUID> all = new ArrayList<>();
            for (UUID uuid : corePlayer.getTeleportRequests().keySet()) {
                CorePlayer fromRequests = this.plugin.getCorePlayerCache().findByUuid(uuid);
                if (fromRequests == null || fromRequests.source() == null) {
                    continue;
                }

                all.add(uuid);
            }

            all.forEach(it -> acceptTeleport(corePlayer, this.plugin.getCorePlayerCache().findByUuid(it)));

            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }
        Player targetPlayer = targetCorePlayer.source();
        if (targetPlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        acceptTeleport(corePlayer, targetCorePlayer);
    }

    private void acceptTeleport(CorePlayer corePlayer, CorePlayer request) {

        if (!corePlayer.isTeleportRequestFromUuid(request.getUuid())) {
            TextUtil.message(corePlayer.source(), "&8[&4&l!&8] &cTen gracz nie teleportuje sie do ciebie lub prosba już wygasla!");
            corePlayer.removeTeleportRequest(request.getUuid());
            return;
        }

        corePlayer.removeTeleportRequest(request.getUuid());
        request.teleport(corePlayer.source().getLocation(), 5);

        TextUtil.message(corePlayer.source(), "&8[&3&l!&8] &7Pomyslnie &3zaakceptowano &7prosbe o teleportacja od gracza &b" + request.getName());
        TextUtil.message(request.source(), "&8[&3&l!&8] &7Gracz &b" + corePlayer.getName() + " &7zaakceptowal twoja &3prosbe &7o teleportacje!");
    }

}
