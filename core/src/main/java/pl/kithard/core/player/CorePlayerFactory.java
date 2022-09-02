package pl.kithard.core.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

import java.util.UUID;

public class CorePlayerFactory {

    private final CorePlugin plugin;

    public CorePlayerFactory(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public CorePlayer create(UUID uuid, String name, String ip) {
        return new CorePlayer(uuid, name, ip);
    }

    public void loadAll() {
        this.plugin.getMongoService().loadAll(CorePlayer.class)
                .forEach(corePlayer -> {

                    this.plugin.getCorePlayerCache().add(corePlayer);
                    this.plugin.getPlayerRankingService().add(corePlayer);
                    corePlayer.initialize();

                });
    }

    public void saveAll(boolean withReason) {

        long start = System.currentTimeMillis();

        int i = 0;
        for (Player it : Bukkit.getOnlinePlayers()) {
            CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByUuid(it.getUniqueId());

            if (withReason) {
                if (!corePlayer.isNeedSave()) {
                    continue;
                }
            }

            i++;
            corePlayer.setNeedSave(false);
            this.plugin.getMongoService().save(corePlayer);
        }

        long end = System.currentTimeMillis() - start;
        for (Player it : Bukkit.getOnlinePlayers()) {

            if (!it.hasPermission("kithard.admin.notifications")) {
                continue;
            }

            TextUtil.message(it,"&8[&2&l!&8] &aPomyslnie zapisano dane &2" + i + " graczy online &aktorzy potrzebowali zapisu w czasie: &2" + end + "ms&a!");
        }

    }
}
