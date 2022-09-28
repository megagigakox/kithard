package pl.kithard.proxy.auth;

import pl.kithard.proxy.ProxyPlugin;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AuthPlayerSaveTask implements Runnable {

    private final ProxyPlugin plugin;

    public AuthPlayerSaveTask(ProxyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getScheduler().schedule(this.plugin, this, 3, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        this.plugin.getAuthPlayerRepository().updateAll(this.plugin.getAuthPlayerCache().values()
                .stream()
                .filter(AuthPlayer::isNeedSave)
                .collect(Collectors.toSet()));
    }
}
