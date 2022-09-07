package pl.kithard.core.antimacro;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntiMacroCache {

    private final Map<UUID, Integer> uuidClicksPerSecondMap = new ConcurrentHashMap<>();

    public Map<UUID, Integer> getUuidClicksPerSecondMap() {
        return uuidClicksPerSecondMap;
    }
}
