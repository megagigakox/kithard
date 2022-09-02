package pl.kithard.core.player.combat;

import org.bukkit.entity.Player;

public class PlayerCombat {

    private Player lastAttackPlayer, lastAssistPlayer;
    private long lastAttackTime, lastAssistTime;

    public PlayerCombat() {
        this.lastAttackPlayer = null;
        this.lastAssistPlayer = null;

        this.lastAttackTime = 0L;
        this.lastAssistTime = 0L;
    }

    public boolean hasFight() {
        return this.lastAttackTime > System.currentTimeMillis();
    }

    public boolean wasFight() {
        return this.lastAttackPlayer != null;
    }

    public Player getLastAttackPlayer() {
        return lastAttackPlayer;
    }

    public void setLastAttackPlayer(Player lastAttackPlayer) {
        this.lastAttackPlayer = lastAttackPlayer;
    }

    public Player getLastAssistPlayer() {
        return lastAssistPlayer;
    }

    public void setLastAssistPlayer(Player lastAssistPlayer) {
        this.lastAssistPlayer = lastAssistPlayer;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public long getLastAssistTime() {
        return lastAssistTime;
    }

    public void setLastAssistTime(long lastAssistTime) {
        this.lastAssistTime = lastAssistTime;
    }
}
