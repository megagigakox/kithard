package pl.kithard.core.warp;

import java.util.*;

public class WarpCache {

    private final Map<String, Warp> warps = new HashMap<>();

    public void add(Warp warp) {
        this.warps.put(warp.getName(), warp);
    }

    public void remove(Warp warp) {
        this.warps.remove(warp.getName());
    }

    public Warp findByName(String name) {
        return this.warps.get(name);
    }

    public Collection<Warp> values() {
        return warps.values();
    }
}
