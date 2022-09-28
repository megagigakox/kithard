package pl.kithard.core.safe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SafeCache {

    private final Map<Long, Safe> safeMap = new HashMap<>();

    public void add(Safe safe) {
        this.safeMap.put(safe.getId(), safe);
    }

    public Safe create(UUID ownerUUID, String ownerName) {
        Safe safe = new Safe(this.safeMap.size() + 1, ownerUUID, ownerName, ownerName);
        this.add(safe);
        return safe;
    }

    public Safe findById(long id) {
        return this.safeMap.get(id);
    }

    public Safe findByName(String name) {
        for (Safe safe : this.safeMap.values()) {
            if (safe.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
                return safe;
            }
        }

        return null;
    }

    public Collection<Safe> values() {
        return this.safeMap.values();
    }

}
