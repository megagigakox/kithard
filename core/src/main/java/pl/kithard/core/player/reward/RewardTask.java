package pl.kithard.core.player.reward;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

public class RewardTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public RewardTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 20 * 15, 20 * 15);
    }

    @Override
    public void run() {
        for (String s : this.plugin.getRedisService().getAll("rewardsToAssign").values()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(s);
            if (corePlayer == null) {
                continue;
            }

            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(TextUtil.color("       &8&l&m--[&b&l&m---&b&l NAGRODA &b&l&m---&8&l&m]--"));
                Bukkit.broadcastMessage(TextUtil.color(" &7Gracz &f" + s + " &7odebral wlasnie &3nagrode"));
                Bukkit.broadcastMessage(TextUtil.color(" &7za dolaczeniena naszego &9discorda &7serwerowego!"));
                Bukkit.broadcastMessage(TextUtil.color(" &7Nie &bodebrales jeszcze &7swojej nagrody? Wpisz &8/&fnagroda"));
                Bukkit.broadcastMessage("");

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + s + " parent addtemp vip 30d");
            });

            this.plugin.getRedisService().remove("rewardsToAssign", s);
        }
    }
}
