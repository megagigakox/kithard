package pl.kithard.core.grouptp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.player.actionbar.ActionBarNotice;
import pl.kithard.core.player.actionbar.ActionBarNoticeType;

import java.util.concurrent.TimeUnit;

public class GroupTeleportTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public GroupTeleportTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20L);
    }

    @Override
    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        this.plugin.getServer().getOnlinePlayers().forEach(player -> this.check(player, currentTimeMillis));
    }

    public void check(Player player, long currentTimeMillis) {

        GroupTeleport groupTeleport = this.plugin.getGroupTeleportCache().find(player);
        if (groupTeleport != null && player.getWorld().getName().equals("gtp")) {
            if (groupTeleport.getCloseCountdownTime() == 0L) {
                return;
            }
            if (groupTeleport.getCloseCountdownTime() > currentTimeMillis) {
                this.plugin.getActionBarNoticeCache().add(
                        player.getUniqueId(),
                        ActionBarNotice.builder()
                            .type(ActionBarNoticeType.GROUP_TELEPORT_COUNTDOWN)
                            .text("&bZamknięcie areny za: &f" + TimeUtil.formatTimeMillis(groupTeleport.getCloseCountdownTime() - currentTimeMillis))
                            .build()
                );
            }
            else {

                this.plugin.getServer()
                        .getScheduler()
                        .runTask(this.plugin, () -> {
                            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                            this.plugin.getGroupTeleportCache().remove(groupTeleport);
                        });

            }
        } else {
            this.plugin.getActionBarNoticeCache().remove(player.getUniqueId(), ActionBarNoticeType.GROUP_TELEPORT_COUNTDOWN);
        }
    }
}
