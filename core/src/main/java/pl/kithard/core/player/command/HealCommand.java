package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class HealCommand {

    private final CorePlugin plugin;

    public HealCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "heal",
            permission = "kithard.commands.heal",
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:5"
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            player.setFireTicks(0);
            player.setHealth(20);
            player.setFoodLevel(20);

            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

            TextUtil.message(player, "&8(&3&l!&8) &7Zostales pomyslnie &buleczony!");
            return;
        }

        if (!player.hasPermission("kithard.commands.heal.other")) {
            TextUtil.insufficientPermission(player, "kithard.commands.heal.other");
            return;
        }

        Player target = this.plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        target.setFireTicks(0);
        target.setHealth(20);
        target.setFoodLevel(20);

        target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));

        TextUtil.message(player, "&8(&3&l!&8)  &7Pomyslnie &3uleczono &7gracza &b" + target.getName() + "&7!");
    }

}
