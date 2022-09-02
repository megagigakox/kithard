package pl.kithard.core.player.actionbar;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ActionBarNoticeCache {

    private final Map<UUID, Map<ActionBarNoticeType, ActionBarNotice>> actionBars = new ConcurrentHashMap<>();

    public void add(UUID uuid, ActionBarNotice actionBar) {
        Map<ActionBarNoticeType, ActionBarNotice> actionBarNoticeMap = values(uuid);
        ActionBarNotice actionBarNotice = actionBarNoticeMap.get(actionBar.getType());
        if (actionBarNotice != null) {
            actionBarNotice.setText(actionBar.getText());
            return;
        }

        actionBarNoticeMap.put(actionBar.getType(), actionBar);
    }

    public void remove(UUID uuid, ActionBarNoticeType type) {
        values(uuid).remove(type);
    }

    public Map<ActionBarNoticeType, ActionBarNotice> values(UUID uuid) {
        return this.actionBars.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>());
    }


}
