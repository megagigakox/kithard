package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import org.bukkit.entity.Player;
import pl.kithard.core.util.TextUtil;

public class SpeedCommand {

    @FunnyCommand(
            name = "speed",
            permission = "kithard.commands.speed",
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(player, "/speed (1 - 10)");
            return;
        }

        String speedString = args[0];
        double speedInteger = Double.parseDouble(speedString);
        double speedIntegerFloat = speedInteger / 10.0;
        if (speedIntegerFloat > 1.0) {
            TextUtil.message(player,"&8[&4&l!&8] &cMaksymalna wartosc to &410&c!");
            return;
        }

        float speed = (float) speedIntegerFloat;
        if (player.isFlying()) {

            player.setFlySpeed(speed);
            TextUtil.message(player,"&8[&3&l!&8] &7Ustawiles predkosc latania na: &b" + speedInteger);

        } else {

            player.setWalkSpeed(speed);
            TextUtil.message(player,"&8[&3&l!&8] &7Ustawiles predkosc chodzenia na: &b" + speedInteger);
        }
    }
}