package pl.kithard.core.guild.log;

public class GuildLog {

    private final GuildLogType type;
    private final String action;
    private final long date;

    public GuildLog(GuildLogType type, String action) {
        this.type = type;
        this.action = action;
        this.date = System.currentTimeMillis();
    }

    public GuildLogType getType() {
        return type;
    }

    public String getAction() {
        return action;
    }

    public long getDate() {
        return date;
    }
}
