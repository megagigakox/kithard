package pl.kithard.core.player.actionbar;

public class ActionBarNotice {

    private final ActionBarNoticeType type;
    private String text;
    private final long expireTime;

    public ActionBarNotice(ActionBarNoticeType type, String text, long expireTime) {
        this.type = type;
        this.text = text;
        this.expireTime = expireTime;
    }

    public static ActionBarNoticeBuilder builder() {
        return new ActionBarNoticeBuilder();
    }

    public ActionBarNoticeType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getExpireTime() {
        return expireTime;
    }
}
