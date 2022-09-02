package pl.kithard.core.player.cooldown;

public class PlayerCooldown {

    private long helpOpDelay;
    private long tntThrownDelay;
    private long chatDelay;
    private long commandsDelay;
    private long pointsInfoDelay;
    private long rankResetDelay;

    public PlayerCooldown() {
        this.helpOpDelay = 0L;
        this.tntThrownDelay = 0L;
        this.chatDelay = 0L;
        this.commandsDelay = 0L;
        this.pointsInfoDelay = 0L;
        this.rankResetDelay = 0L;
    }

    public long getHelpOpDelay() {
        return helpOpDelay;
    }

    public void setHelpOpDelay(long helpOpDelay) {
        this.helpOpDelay = helpOpDelay;
    }

    public long getTntThrownDelay() {
        return tntThrownDelay;
    }

    public void setTntThrownDelay(long tntThrownDelay) {
        this.tntThrownDelay = tntThrownDelay;
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
}
