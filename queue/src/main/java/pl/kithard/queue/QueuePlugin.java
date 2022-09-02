package pl.kithard.queue;

import org.bukkit.plugin.java.JavaPlugin;
import pl.kithard.queue.queue.*;

public class QueuePlugin extends JavaPlugin {

    private QueuePlayerCache queuePlayerCache;

    @Override
    public void onEnable() {
        super.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new QueuePlayerAddListener(this));
        super.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.queuePlayerCache = new QueuePlayerCache();

        new QueueRedirectTask(this, "main");
        new QueueInfoTask(this);
        new QueueListener(this);
    }

    public QueuePlayerCache getQueuePlayerCache() {
        return queuePlayerCache;
    }
}
