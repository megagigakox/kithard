package pl.kithard.proxy.auth.task;

import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;

import java.util.concurrent.TimeUnit;

public class AuthPlayerDatabaseSaveTask implements Runnable {

    private final ProxyPlugin plugin;

    public AuthPlayerDatabaseSaveTask(ProxyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getScheduler().schedule(plugin, this, 30, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        for (AuthPlayer authPlayer : this.plugin.getAuthPlayerCache().values()) {
            if (authPlayer.isNeedSave()) {
                this.plugin.getMongoService().save(authPlayer);
                authPlayer.setNeedSave(false);
            }
        }
    }
}
