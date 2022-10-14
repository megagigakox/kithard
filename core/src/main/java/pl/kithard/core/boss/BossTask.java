package pl.kithard.core.boss;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

public class BossTask extends BukkitRunnable {

    private final CorePlugin plugin;


    public BossTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 40L);
    }

    @Override
    public void run() {
        this.plugin.getServer().getOnlinePlayers().forEach(this::start);
    }

    void start(Player player) {
        BPlayerBoard bPlayerBoard = Netherboard.instance().getBoard(player);
        if (this.plugin.getBossService().getBoss() == null) {
            if (bPlayerBoard != null) {
                bPlayerBoard.clear();
                bPlayerBoard.delete();
            }
            return;
        }

        if (bPlayerBoard == null) {
            bPlayerBoard = Netherboard.instance().createBoard(player, "boss");
            bPlayerBoard.setName(TextUtil.color("&b&lEVENT BOSS"));
        }

        Zombie zombie = this.plugin.getBossService().getBoss();
        bPlayerBoard.set(TextUtil.color("&7Aktualnie na mapie zrespiono"), 9);
        bPlayerBoard.set(TextUtil.color("&7poteznego &3wujka Władka&7!"), 8);
        bPlayerBoard.set(TextUtil.color("&7Za pokonanie go..."), 7);
        bPlayerBoard.set(TextUtil.color("&7Zdobedziesz &fcenne &7nagrody!"), 6);
        bPlayerBoard.set(" ", 5);
        bPlayerBoard.set(TextUtil.color("    &7Boss aktualnie posiada&8: &c"), 4);
        bPlayerBoard.set(TextUtil.color("            &c" + MathUtil.round(zombie.getHealth(), 2) + " HP!"), 3);
        bPlayerBoard.set(TextUtil.color("    &7Aktualne kordy bossa&8:"), 2);
        bPlayerBoard.set(
                TextUtil.color("        &7x&8: &b{COORDSX} &7z&8: &b{COORDSZ}")
                        .replace("{COORDSX}", String.valueOf(zombie.getLocation().getBlockX()))
                        .replace("{COORDSZ}", String.valueOf(zombie.getLocation().getBlockZ())),
                1);

    }
}
