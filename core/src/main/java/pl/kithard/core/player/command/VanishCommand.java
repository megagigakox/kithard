package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class VanishCommand {

    @FunnyCommand(
            name = "vanish",
            aliases = "v",
            playerOnly = true,
            acceptsExceeded = true,
            permission = "kithard.commands.vanish"
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {

            if (!corePlayer.isVanish()) {
                for (Player it : Bukkit.getOnlinePlayers()) {
                    it.hidePlayer(player);
                }

                corePlayer.setVanish(true);

            } else {
                for (Player it : Bukkit.getOnlinePlayers()) {
                    it.showPlayer(player);
                }

                corePlayer.setVanish(false);
            }

            TextUtil.message(player,"&8[&3&l!&8] &7Pomyslnie &3zmieniles &7tryb vanisha na: " + (corePlayer.isVanish() ? "&aON" : "&cOFF"));
        }
    }

}
