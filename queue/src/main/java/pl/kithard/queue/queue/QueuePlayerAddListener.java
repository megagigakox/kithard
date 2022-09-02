package pl.kithard.queue.queue;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import pl.kithard.queue.QueuePlugin;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class QueuePlayerAddListener implements PluginMessageListener {

    private final QueuePlugin plugin;

    public QueuePlayerAddListener(QueuePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        System.out.println("dobry chanel");
        if (!s.equalsIgnoreCase("BungeeCord") ) {
            System.out.println("zly chanel");
            return;
        }

        try {
            System.out.println("w trycatch");

            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
            DataInputStream in = new DataInputStream(stream);
            String data1 = in.readUTF();
            String data2 = in.readUTF();

            QueuePlayer queuePlayer = new QueuePlayer(UUID.fromString(data1), data2);
            this.plugin.getQueuePlayerCache().add(queuePlayer);
            System.out.println("Dodano " + queuePlayer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
