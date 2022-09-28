package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class HelpOpCommand {

    @FunnyCommand(
            name = "helpop",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/helpop (wiadomosc)");
            return;
        }

        if (corePlayer.getCooldown().getHelpopCooldown() > System.currentTimeMillis()) {
            TextUtil.message(player, "&8(&4&l!&8) &cWiadomosc do administracji możesz wyslac ponownie za &4" +
                    TimeUtil.formatTimeMillis(corePlayer.getCooldown().getHelpopCooldown() - System.currentTimeMillis()));
            return;
        }

        String message = StringUtils.join(args, " ");
        for (Player it : Bukkit.getOnlinePlayers()) {

            if (it.hasPermission("kithard.helpop.access")) {
                TextUtil.message(it, "&8[&4HELPOP&8] &7{PLAYER} &8&m-&8> &c{MESSAGE}"
                        .replace("{PLAYER}", player.getName())
                        .replace("{MESSAGE}", message));
            }
        }

        corePlayer.getCooldown().setHelpopCooldown(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30));
        TextUtil.message(player, "&8(&3&l!&8) &7Twoja &fwiadomosc &7zostala wyslana!");
    }

}
