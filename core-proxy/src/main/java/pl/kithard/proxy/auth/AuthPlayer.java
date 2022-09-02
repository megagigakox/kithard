package pl.kithard.proxy.auth;

import pl.kithard.core.api.entity.DatabaseEntity;
import pl.kithard.core.api.entry.DatabaseEntry;

@DatabaseEntity(database = "kithard", collection = "auth-players")
public class AuthPlayer extends DatabaseEntry {

    private final String name;
    private String password;

    private boolean premium, logged, registered;

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
}
