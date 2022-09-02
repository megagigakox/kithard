package pl.kithard.core.drop.util;

import org.bukkit.entity.Player;
import pl.kithard.core.drop.DropItem;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.settings.ServerSettings;

public final class DropUtil {

    private DropUtil() {}

    public static double calculateChanceFromStone(DropItem dropItem, CorePlayer corePlayer, ServerSettings serverSettings) {
        double chance = dropItem.getChance();

        if (corePlayer.getTurboDrop() > System.currentTimeMillis() || serverSettings.getTurboDrop() > System.currentTimeMillis()) {
            chance *= 1.5;
        }

        Player player = corePlayer.source();
        if (player.hasPermission("kithard.dropbonus.legenda")) {
            chance *= 1.20;
        }
        else if (player.hasPermission("kithard.dropbonus.mvp")) {
            chance *= 1.15;
        }
        else if (player.hasPermission("kithard.dropbonus.svip")) {
            chance *= 1.10;
        }

        return chance;
    }

}
