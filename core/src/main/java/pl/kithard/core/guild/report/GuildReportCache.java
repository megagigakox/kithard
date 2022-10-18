package pl.kithard.core.guild.report;

import java.util.*;

public class GuildReportCache {

    private final Map<String, GuildReport> guildReports = new HashMap<>();

    public GuildReport find(String tag) {
        return this.guildReports.get(tag);
    }

    public void add(GuildReport guildReport) {
        this.guildReports.put(guildReport.getGuild(), guildReport);
    }

    public void remove(GuildReport guildReport) {
        this.guildReports.remove(guildReport.getGuild());
    }

    public Collection<GuildReport> values() {
        return this.guildReports.values();
    }
}
