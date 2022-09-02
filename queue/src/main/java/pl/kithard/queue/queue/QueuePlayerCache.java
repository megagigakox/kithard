package pl.kithard.queue.queue;

import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QueuePlayerCache {

    private final Map<String, LinkedList<QueuePlayer>> queues = new ConcurrentHashMap<>();

    public QueuePlayerCache() {
        this.queues.put("main", new LinkedList<>());
    }

    public void add(QueuePlayer queuePlayer) {
        this.findPlayersFromQueue(queuePlayer.getTransferServer())
                .add(queuePlayer);
    }

    public void remove(QueuePlayer queuePlayer) {
        this.findPlayersFromQueue(queuePlayer.getTransferServer())
                .remove(queuePlayer);
    }

    public List<QueuePlayer> findPlayersFromQueue(String queue) {
        return this.queues.get(queue);
    }

    public QueuePlayer findPlayer(UUID uuid) {
        for (Map.Entry<String, LinkedList<QueuePlayer>> list : queues.entrySet()) {
            for (QueuePlayer queuePlayer : list.getValue()) {
                if (queuePlayer.getUUID().equals(uuid)) {
                    return queuePlayer;
                }
            }
        }
        return null;
    }

    public List<QueuePlayer> getAllPlayers() {
        List<QueuePlayer> queuePlayers = new ArrayList<>();
        for (Map.Entry<String, LinkedList<QueuePlayer>> list : queues.entrySet()) {
            queuePlayers.addAll(list.getValue());
        }
        return queuePlayers;
    }

    public int getPlace(QueuePlayer queuePlayer) {
        return this.findPlayersFromQueue(queuePlayer.getTransferServer())
                .indexOf(queuePlayer);
    }

}