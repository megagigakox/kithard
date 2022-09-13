package pl.kithard.core.player.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCooldown {

    private long helpopCooldown;
    private long chatCooldown;
    private long commandsCooldown;
    private long pointsInfoCooldown;
    private long rankResetCooldown;
    private long groupTeleportCombatCooldown;
    private final Map<String, Long> kitCooldowns = new HashMap<>();
    private final Map<UUID, Long> lastKillersCooldown = new HashMap<>();

    public long getHelpopCooldown() {
        return helpopCooldown;
    }

    public void setHelpopCooldown(long helpopCooldown) {
        this.helpopCooldown = helpopCooldown;
    }

    public long getChatCooldown() {
        return chatCooldown;
    }

    public void setChatCooldown(long chatCooldown) {
        this.chatCooldown = chatCooldown;
    }

    public long getCommandsCooldown() {
        return commandsCooldown;
    }

    public void setCommandsCooldown(long commandsCooldown) {
        this.commandsCooldown = commandsCooldown;
    }

    public long getPointsInfoCooldown() {
        return pointsInfoCooldown;
    }

    public void setPointsInfoCooldown(long pointsInfoCooldown) {
        this.pointsInfoCooldown = pointsInfoCooldown;
    }

    public long getRankResetCooldown() {
        return rankResetCooldown;
    }

    public void setRankResetCooldown(long rankResetCooldown) {
        this.rankResetCooldown = rankResetCooldown;
    }

    public long getGroupTeleportCombatCooldown() {
        return groupTeleportCombatCooldown;
    }

    public void setGroupTeleportCombatCooldown(long groupTeleportCombatCooldown) {
        this.groupTeleportCombatCooldown = groupTeleportCombatCooldown;
    }

    public Map<String, Long> getKitCooldowns() {
        return kitCooldowns;
    }

    public long getKitCooldown(String kitName){
        return this.kitCooldowns.getOrDefault(kitName, (long) 0);
    }

    public Map<UUID, Long> getLastKillersCooldown() {
        return lastKillersCooldown;
    }
}
