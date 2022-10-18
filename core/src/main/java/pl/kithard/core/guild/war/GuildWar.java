package pl.kithard.core.guild.war;

import java.util.concurrent.TimeUnit;

public class GuildWar {

    private final String enemyGuild;
    private final long expireTime;

    private int kills, deaths, points;
    public GuildWar(String enemyGuild) {
        this.enemyGuild = enemyGuild;
        this.expireTime = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12);
    }

    public String getEnemyGuild() {
        return enemyGuild;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getPoints() {
        return points;
    }
}
