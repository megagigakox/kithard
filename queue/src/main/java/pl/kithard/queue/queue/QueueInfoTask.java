package pl.kithard.queue.queue;

import org.bukkit.entity.Player;
import pl.kithard.queue.QueuePlugin;
import pl.kithard.queue.util.TitleUtil;

public class QueueInfoTask implements Runnable {

    private final QueuePlugin plugin;

    public QueueInfoTask(QueuePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 20L, 20L);
    }

    @Override
    public void run() {
        this.plugin.getQueuePlayerCache().getAllPlayers().forEach(this::send);
    }

    void send(QueuePlayer queuePlayer) {
        int position = this.plugin.getQueuePlayerCache().getPlace(queuePlayer);
        Player player = this.plugin.getServer().getPlayer(queuePlayer.getUUID());
        if (player == null) {
            return;
        }

        if (position == 0) {
            TitleUtil.title(player,
                    " ",
                    "&7Trwa proba laczenia z serwerem: &b&l" + queuePlayer.getTransferServer(),
                    0,
                    40,
                    0
            );
            return;
        }

        TitleUtil.title(
                player,
                " ",
                "&7Aktualnie jesteś &b&l" + (position + 1) + " &7w kolejce!",
                0,
                40,
                0
        );
    }
}
