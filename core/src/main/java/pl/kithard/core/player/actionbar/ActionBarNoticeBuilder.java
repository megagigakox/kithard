package pl.kithard.core.player.actionbar;

public class ActionBarNoticeBuilder {

    private ActionBarNoticeType type;
    private String text;
    private long expireTime;

    public ActionBarNoticeBuilder type(ActionBarNoticeType type) {
        this.type = type;
        return this;
    }

    public ActionBarNoticeBuilder text(String text) {
        this.text = text;
        return this;
    }

    public ActionBarNoticeBuilder expireTime(long expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public ActionBarNotice build() {
        return new ActionBarNotice(type, text, expireTime);
    }
}