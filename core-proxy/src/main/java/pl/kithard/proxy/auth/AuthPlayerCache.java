package pl.kithard.proxy.auth;

import pl.kithard.proxy.ProxyPlugin;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthPlayerCache {

    private final ProxyPlugin plugin;
    private final Map<String, AuthPlayer> authPlayerMap = new ConcurrentHashMap<>();

    public AuthPlayerCache(ProxyPlugin plugin) {
        this.plugin = plugin;
    }

    public AuthPlayer create(String name) {
        AuthPlayer authPlayer = new AuthPlayer(name);
        this.authPlayerMap.put(authPlayer.getName().toLowerCase(), authPlayer);
        return authPlayer;
    }

    public AuthPlayer findByName(String name) {
        return this.authPlayerMap.get(name.toLowerCase());
    }

    public boolean hasMaxAccountsPerIP(String ip) {
        int accounts = 0;
        for (AuthPlayer authPlayer : this.authPlayerMap.values()) {
            if (authPlayer.getIp().equals(ip)) {
                accounts++;
            }
        }

        return accounts > 3;
    }

    public void load() {
        this.plugin.getMongoService()
                .loadAll(AuthPlayer.class)
                .forEach(authPlayer -> this.authPlayerMap.put(authPlayer.getName().toLowerCase(), authPlayer));
    }

    public Collection<AuthPlayer> values() {
        return Collections.unmodifiableCollection(this.authPlayerMap.values());
    }

}
