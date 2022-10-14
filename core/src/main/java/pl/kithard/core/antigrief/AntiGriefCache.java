package pl.kithard.core.antigrief;

import java.util.HashSet;
import java.util.Set;

public class AntiGriefCache {

    private final Set<AntiGriefBlock> antiGriefBlocks = new HashSet<>();

    public Set<AntiGriefBlock> getAntiGriefBlocks() {
        return antiGriefBlocks;
    }
}
