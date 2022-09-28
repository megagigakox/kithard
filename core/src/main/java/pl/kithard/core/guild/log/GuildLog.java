package pl.kithard.core.guild.log;

public class GuildLog {

    private final String guild;

    private final GuildLogType type;
    private final String action;
    private final long date;

    public GuildLog(String guild, GuildLogType type, String action) {
        this.guild = guild;
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

    public String getGuild() {
        return guild;
    }
}
