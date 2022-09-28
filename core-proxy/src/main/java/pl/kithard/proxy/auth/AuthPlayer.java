package pl.kithard.proxy.auth;

import pl.kithard.core.api.database.entry.DatabaseEntry;

public class AuthPlayer extends DatabaseEntry {

    private final String name;
    private String password, ip;

    private long firstJoinTime;
    private boolean premium, registered;

    private long joinCooldown;
    private boolean logged;

    public AuthPlayer(String name, String password, String ip, long firstJoinTime, boolean premium, boolean registered) {
        this.name = name;
        this.password = password;
        this.ip = ip;
        this.firstJoinTime = firstJoinTime;
        this.premium = premium;
        this.registered = registered;
    }

    public AuthPlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public long getFirstJoinTime() {
        return firstJoinTime;
    }

    public void setFirstJoinTime(long firstJoinTime) {
        this.firstJoinTime = firstJoinTime;
    }

    public long getJoinCooldown() {
        return joinCooldown;
    }

    public void setJoinCooldown(long joinCooldown) {
        this.joinCooldown = joinCooldown;
    }
}
