package pl.kithard.core.warp;

import eu.okaeri.configs.OkaeriConfig;

import java.util.*;

public class WarpCache extends OkaeriConfig {

    private Map<String, Warp> warps = new HashMap<>();

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
