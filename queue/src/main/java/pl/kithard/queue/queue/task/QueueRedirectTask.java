package pl.kithard.queue.queue.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.queue.QueuePlugin;
import pl.kithard.queue.queue.QueuePlayer;
import pl.kithard.queue.util.TextUtil;
import pl.kithard.queue.util.TitleUtil;
import pl.kithard.queue.util.TransferUtil;

import java.util.List;

public class QueueRedirectTask implements Runnable {

    private final QueuePlugin plugin;
    private final String queue;

    public QueueRedirectTask(QueuePlugin plugin, String queue) {
        this.plugin = plugin;
        this.queue = queue;

        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this,  0L, 40L);
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

        if (queuePlayer.getAttempts() == 3) {
            TextUtil.message(player, "&cPrzekroczono maksymalna ilosc prob polaczenia z serwerem - przenosze na koniec kolejki.");
            this.plugin.getQueuePlayerCache().remove(queuePlayer);
            this.plugin.getQueuePlayerCache().add(new QueuePlayer(queuePlayer.getUUID(), queue));
            return;
        }

        String serverName = queuePlayer.getTransferServer();
        queuePlayer.incrementAttempt();
        TransferUtil.transfer(player, serverName, this.plugin);

    }
}
