package pl.kithard.core.player.actionbar;

public enum ActionBarNoticeType {

    VANISH(1),
    ANTI_LOGOUT(2),
    HP_INFO(3),
    TURBO_DROP(4),
    PROTECTION(5),
    NEED_HELP(6),
    GUILD_TERRAIN(7),
    GUILD_TERRAIN_REGEN(8),
    STONE_DROP(9),
    PERISCOPE(10),
    TNT(11);

    private final int priority;

    ActionBarNoticeType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
