package pl.kithard.core.player.cooldown;

public class PlayerCooldown {

    private long helpOpDelay;
    private long chatDelay;
    private long commandsDelay;
    private long pointsInfoDelay;
    private long rankResetDelay;
    private long gtpFightDelay;

    public long getHelpOpDelay() {
        return helpOpDelay;
    }

    public void setHelpOpDelay(long helpOpDelay) {
        this.helpOpDelay = helpOpDelay;
    }

    public long getChatDelay() {
        return chatDelay;
    }

    public void setChatDelay(long chatDelay) {
        this.chatDelay = chatDelay;
    }

    public long getCommandsDelay() {
        return commandsDelay;
    }

    public void setCommandsDelay(long commandsDelay) {
        this.commandsDelay = commandsDelay;
    }

    public long getPointsInfoDelay() {
        return pointsInfoDelay;
    }

    public void setPointsInfoDelay(long pointsInfoDelay) {
        this.pointsInfoDelay = pointsInfoDelay;
    }

    public long getRankResetDelay() {
        return rankResetDelay;
    }

    public void setRankResetDelay(long rankResetDelay) {
        this.rankResetDelay = rankResetDelay;
    }

    public long getGtpFightDelay() {
        return gtpFightDelay;
    }

    public void setGtpFightDelay(long gtpFightDelay) {
        this.gtpFightDelay = gtpFightDelay;
    }
}
