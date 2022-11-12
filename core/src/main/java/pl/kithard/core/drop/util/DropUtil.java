package pl.kithard.core.drop.util;

import org.bukkit.entity.Player;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettings;

public final class DropUtil {

    private DropUtil() {}

    public static double calculateChanceFromStone(DropItem dropItem, CorePlayer corePlayer, ServerSettings serverSettings, Guild guild) {
        double chance = dropItem.getChance();

        long currentTimeMilis = System.currentTimeMillis();
        if (corePlayer.getTurboDrop() > currentTimeMilis || serverSettings.getTurboDrop() > currentTimeMilis || (guild != null && guild.getTurboDrop() > currentTimeMilis)) {
            chance *= 1.5;
        }

        Player player = corePlayer.source();
        if (player.hasPermission("kithard.dropbonus.hard")) {
            chance *= 1.20;
        }
        else if (player.hasPermission("kithard.dropbonus.sponsor")) {
            chance *= 1.15;
        }
        else if (player.hasPermission("kithard.dropbonus.svip")) {
            chance *= 1.10;
        }

        return chance;
    }

}
