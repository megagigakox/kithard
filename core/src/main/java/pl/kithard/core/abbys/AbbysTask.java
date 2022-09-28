package pl.kithard.core.abbys;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.TextUtil;

public class AbbysTask extends BukkitRunnable {

    public static int TIME = 0;

    private final CorePlugin plugin;

    public AbbysTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        TIME++;

        if (TIME == 350) {
            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Usuwanie itemow z mapy nastapi za&8: &f10 sekund"));
        }
        else if (TIME == 357) {
            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Usuwanie itemow z mapy nastapi za&8: &f3 sekundy"));
        }
        else if (TIME == 358) {
            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Usuwanie itemow z mapy nastapi za&8: &f2 sekundy"));
        }
        else if (TIME == 359) {
            Bukkit.broadcastMessage(TextUtil.color("&8(&3&l!&8) &7Usuwanie itemow z mapy nastapi za&8: &f1 sekunde"));
        }
        else if (TIME >= 360) {

            World world = Bukkit.getWorld("world");
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }

            Bukkit.broadcastMessage(TextUtil.color("&8(&2&l!&8) &aPomyslnie usunieto wszystkie itemy!"));
            TIME = 0;
        }

    }
}
