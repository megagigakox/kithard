package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class RankingResetCommand {

    @FunnyCommand(
            name = "resetujranking",
            aliases = {"rankreset", "resetrank"},
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, CorePlayer corePlayer) {
        if (corePlayer.getCooldown().getRankResetDelay() > System.currentTimeMillis()) {
            TextUtil.message(player, "&8[&4&l!&8] &cNastepny raz ranking bedziesz mogl zresetowac za &4" +
                    TimeUtil.formatTimeMillis(corePlayer.getCooldown().getRankResetDelay() - System.currentTimeMillis()));
            return;
        }

        if (!InventoryUtil.hasItem(player, Material.EMERALD_BLOCK, 256)) {
            TextUtil.message(player, "&8[&4&l!&8] &cAby zresetowac swoj ranking potrzebujesz &4256 blokow emeraldow&c! Aktualnie posiadasz: &4" +
                    InventoryUtil.countItemsIgnoreItemMeta(player, new ItemStack(Material.EMERALD_BLOCK, 256)) + "&8/&4256&c!");
            return;
        }

        InventoryUtil.removeItem(player, Material.EMERALD_BLOCK, 256);

        corePlayer.setPoints(500);
        corePlayer.setAssists(0);
        corePlayer.setKills(0);
        corePlayer.setDeaths(0);
        corePlayer.setKillStreak(0);
        corePlayer.getCooldown().setRankResetDelay(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(3));
        corePlayer.setNeedSave(true);

        TextUtil.message(player, "&8[&2&l!&8] &aPomyslnie zresetowano twoj ranking!");
    }
}
