package pl.kithard.queue.queue;

import org.bukkit.entity.Player;
import pl.kithard.queue.QueuePlugin;
import pl.kithard.queue.util.TitleUtil;
import pl.kithard.queue.util.TransferUtil;

import java.util.List;

public class QueueRedirectTask implements Runnable {

    private final QueuePlugin plugin;
    private final String queue;

    public QueueRedirectTask(QueuePlugin plugin, String queue) {
        this.plugin = plugin;
        this.queue = queue;

        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 100L, 100L);
    }

    @Override
    public void run() {
        List<QueuePlayer> queuePlayers = this.plugin.getQueuePlayerCache().findPlayersFromQueue(queue);
        if (queuePlayers.size() == 0) {
            return;
        }

        QueuePlayer queuePlayer = queuePlayers.get(0);
        Player player = this.plugin.getServer().getPlayer(queuePlayer.getUUID());
        if (player == null) {
            return;
        }

        String serverName = queuePlayer.getTransferServer();
        TransferUtil.transfer(player, serverName, this.plugin);

    }
}
