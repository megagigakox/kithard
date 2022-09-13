package pl.kithard.core.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.database.entry.DatabaseEntry;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.util.TextUtil;

import java.util.UUID;
import java.util.stream.Collectors;

public class CorePlayerFactory {

    private final CorePlugin plugin;

    public CorePlayerFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public CorePlayer create(UUID uuid, String name, String ip) {
        return new CorePlayer(uuid, name, ip);
    }

    public void loadAll() {
        this.plugin.getCorePlayerRepository().loadAll()
                .forEach(corePlayer -> {

                    this.plugin.getCorePlayerCache().add(corePlayer);
                    this.plugin.getPlayerRankingService().add(corePlayer);

                });
    }

    public void saveAll(boolean withReason) {

        long start = System.currentTimeMillis();

        this.plugin.getCorePlayerRepository().updateAll(withReason ? this.plugin.getCorePlayerCache().getValues()
                        .stream()
                        .filter(DatabaseEntry::isNeedSave)
                        .collect(Collectors.toSet())
                        : this.plugin.getCorePlayerCache().getValues()
                );

        long end = System.currentTimeMillis() - start;
        for (Player it : Bukkit.getOnlinePlayers()) {
            if (!it.hasPermission("kithard.admin.notifications")) {
                continue;
            }

            TextUtil.message(it,"&8[&2&l!&8] &aPomyslnie zapisano dane &2wszystkich graczy online &aktorzy potrzebowali zapisu w czasie: &2" + end + "ms&a!");
        }

    }
}
