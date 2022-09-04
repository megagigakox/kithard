package pl.kithard.queue.queue;

import java.io.Serializable;
import java.util.UUID;

public class QueuePlayer implements Serializable {

    private final UUID uuid;
    private final String transferServer;

    private int attempts;

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

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void incrementAttempt() {
        this.attempts++;
    }
}