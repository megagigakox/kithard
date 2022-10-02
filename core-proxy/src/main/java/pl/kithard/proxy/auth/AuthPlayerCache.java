package pl.kithard.proxy.auth;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AuthPlayerCache {

    private final Map<String, AuthPlayer> authPlayerMap = new ConcurrentHashMap<>();

    public void add(AuthPlayer authPlayer) {
        this.authPlayerMap.put(authPlayer.getName().toLowerCase(), authPlayer);
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
        List<AuthPlayer> authPlayers = findAccountsByIP(ip);
        if (authPlayers == null || authPlayers.isEmpty()) {
            return false;
        }
        return authPlayers.size() > 2;
    }

    public List<AuthPlayer> findAccountsByIP(String ip) {
        List<AuthPlayer> authPlayers = new ArrayList<>();
        for (AuthPlayer authPlayer : this.authPlayerMap.values()) {
            if (authPlayer.getIp().equals(ip)) {
                authPlayers.add(authPlayer);
            }
        }

        return authPlayers;
    }

    public Collection<AuthPlayer> values() {
        return Collections.unmodifiableCollection(this.authPlayerMap.values());
    }

}
