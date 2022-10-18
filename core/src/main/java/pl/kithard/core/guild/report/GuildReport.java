package pl.kithard.core.guild.report;

public class GuildReport {

    private final String guild;
    private final String sender;
    private final String reason;
    private final long date;

    public GuildReport(String guild, String sender, String reason) {
        this.guild = guild;
        this.sender = sender;
        this.reason = reason;
        this.date = System.currentTimeMillis();
    }

    public String getGuild() {
        return guild;
    }

    public String getSender() {
        return sender;
    }

    public String getReason() {
        return reason;
    }

    public long getDate() {
        return date;
    }
}
