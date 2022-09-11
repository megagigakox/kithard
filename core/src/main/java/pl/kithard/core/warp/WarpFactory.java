package pl.kithard.core.warp;

import org.bukkit.Location;
import org.bukkit.Material;
import pl.kithard.core.CorePlugin;

public class WarpFactory {

    private final CorePlugin plugin;

    public WarpFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void create(String name, Location location, Material icon) {

        Warp warp = new Warp(name, location, icon);
        this.plugin.getWarpCache().add(warp);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
                this.plugin.getMongoService().insert(warp));
    }

    public void delete(Warp warp) {
        this.plugin.getWarpCache().remove(warp);

        this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () ->
                this.plugin.getMongoService().delete(warp));
    }

    public void loadAll() {

        this.plugin.getMongoService()
                .loadAll(Warp.class)
                .forEach(warp -> this.plugin.getWarpCache().add(warp));

    }

}
