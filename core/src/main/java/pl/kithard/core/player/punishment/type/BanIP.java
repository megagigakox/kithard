package pl.kithard.core.player.punishment.type;

import com.google.gson.annotations.SerializedName;
import pl.kithard.core.api.entity.DatabaseEntity;

@DatabaseEntity(database = "core", collection = "punishments-banip")
public class BanIP {

    @SerializedName("_id")
    private final String punishedIP;
    private final String admin;
    private final String reason;

    public BanIP(String punishedIP, String admin, String reason) {
        this.punishedIP = punishedIP;
        this.admin = admin;
        this.reason = reason;
    }

    public String getPunishedIP() {
        return punishedIP;
    }

    public String getAdmin() {
        return admin;
    }

    public String getReason() {
        return reason;
    }
}
