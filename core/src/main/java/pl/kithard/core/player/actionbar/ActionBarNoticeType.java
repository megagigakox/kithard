package pl.kithard.core.player.actionbar;

public enum ActionBarNoticeType {

    VANISH(1),
    ANTI_LOGOUT(2),
    HP_INFO(3),
    TURBO_DROP(4),
    PROTECTION(5),
    GUILD_TERRAIN(6),
    GUILD_TERRAIN_REGEN(7),
    STONE_DROP(8),
    PERISCOPE(9);

    private final int priority;

    ActionBarNoticeType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
