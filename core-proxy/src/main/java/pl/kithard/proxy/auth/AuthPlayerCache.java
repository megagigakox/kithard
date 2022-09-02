package pl.kithard.proxy.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthPlayerCache {

    private final Map<String, AuthPlayer> authPlayerMap = new ConcurrentHashMap<>();

    public void add(AuthPlayer authPlayer) {
        this.authPlayerMap.put(authPlayer.getName(), authPlayer);
    }

    public AuthPlayer findByName(String name) {
        return this.authPlayerMap.get(name);
    }

    public Collection<AuthPlayer> values() {
        return Collections.unmodifiableCollection(this.authPlayerMap.values());
    }

}
