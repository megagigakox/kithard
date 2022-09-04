package pl.kithard.core.player.punishment.type;

import com.google.gson.annotations.SerializedName;
import pl.kithard.core.api.database.entity.DatabaseEntity;

@DatabaseEntity(database = "core", collection = "punishments-ban")
public class Ban {

    @SerializedName("_id")
    private final String punished;

    private final String admin;
    private final long time;
    private final String reason;

    public Ban(String punished, String admin, long time, String reason) {
        this.punished = punished;
        this.admin = admin;
        this.time = time;
        this.reason = reason;
    }

    public String getPunished() {
        return punished;
    }

    public String getAdmin() {
        return admin;
    }

    public long getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }
}
