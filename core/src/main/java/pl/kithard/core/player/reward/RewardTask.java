package pl.kithard.core.player.reward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;
import pl.kithard.core.util.TextUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RewardTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public RewardTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20 * 15L);
    }

    @Override
    public void run() {

        for (String s : this.plugin.getRewardRepository().loadAllWhoNeedClaim()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(s);
            if (corePlayer == null) {
                continue;
            }

            Player player = corePlayer.source();
            if (player == null) {
                continue;
            }

            this.plugin.getRewardRepository().updateMinecraftAccount(s, true);
            if (!corePlayer.isDisabledSetting(PlayerSettings.REWARDS)) {

                Bukkit.getScheduler().runTask(plugin, () -> {

                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(TextUtil.color("       &8&l&m--[&b&l&m---&b&l NAGRODA &b&l&m---&8&l&m]--"));
                    Bukkit.broadcastMessage(TextUtil.color(" &7Gracz &f" + s + " &7odebral wlasnie &3nagrode"));
                    Bukkit.broadcastMessage(TextUtil.color(" &7za dolaczeniena naszego &9discorda &7serwerowego!"));
                    Bukkit.broadcastMessage(TextUtil.color(" &7Nie &bodebrales jeszcze &7swojej nagrody? Wpisz &8/&fnagroda"));
                    Bukkit.broadcastMessage("");

                    InventoryUtil.addItem(player, ItemStackBuilder.of(CustomRecipe.MAGIC_CHEST.getItem().clone())
                            .amount(3)
                            .asItemStack());

                });

            }



        }
    }
}
