package pl.kithard.queue.queue.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.queue.QueuePlugin;
import pl.kithard.queue.queue.QueuePlayer;
import pl.kithard.queue.util.ActionBarUtil;
import pl.kithard.queue.util.TitleUtil;
import pl.kithard.queue.util.TransferUtil;

public class QueueInfoTask implements Runnable {

    private final QueuePlugin plugin;

    public QueueInfoTask(QueuePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 10L, 10L);
    }

    @Override
    public void run() {
        this.plugin.getQueuePlayerCache().getAllPlayers().forEach(queuePlayer -> {
            send(queuePlayer);
            ActionBarUtil.actionBar(Bukkit.getPlayer(queuePlayer.getUUID()), "&r&8&l&m--[&r&b&l&m---&r&3&l Kit&r&b&lHard.pl &r&b&l&m---&r&8&l&m]--&r");
        });
    }

    void send(QueuePlayer queuePlayer) {
        int position = this.plugin.getQueuePlayerCache().getPlace(queuePlayer);
        Player player = this.plugin.getServer().getPlayer(queuePlayer.getUUID());
        if (player == null) {
            return;
        }

        if (player.hasPermission("bypass.queue")) {
            TransferUtil.transfer(player, "main", this.plugin);
            return;
        }

        if (position == 0) {
            TitleUtil.title(player,
                    " ",
                    "&7Trwa proba laczenia z serwerem&8: &b&l" + queuePlayer.getTransferServer() + " &8(&e" + queuePlayer.getAttempts() + "&8/&e3&8)",
                    0,
                    40,
                    0
            );
            return;
        }

        TitleUtil.title(
                player,
                " ",
                "&7Aktualnie jestes&8: &b&l" + (position + 1) + "&8/&3&l" + this.plugin.getQueuePlayerCache().getAllPlayers().size() + " &7w kolejce!",
                0,
                40,
                0
        );
    }
}
