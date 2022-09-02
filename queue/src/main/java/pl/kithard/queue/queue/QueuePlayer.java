package pl.kithard.queue.queue;

import java.io.Serializable;
import java.util.UUID;

public class QueuePlayer implements Serializable {

    private final UUID uuid;
    private final String transferServer;

    public QueuePlayer(UUID uuid, String transferServer) {
        this.uuid = uuid;
        this.transferServer = transferServer;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getTransferServer() {
        return transferServer;
    }
}