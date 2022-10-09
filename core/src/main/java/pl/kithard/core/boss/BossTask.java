package pl.kithard.core.boss;

import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.MathUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;
import java.util.List;

public class BossTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public BossTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(plugin, 0L, 50L);
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

        IronGolem zombie = this.plugin.getBossService().getBoss();
        List<String> a = TextUtil.color(Arrays.asList(
                "",
                "&7Aktualnie na mapie zrespiono",
                "&7poteznego &3wujka Władka&7!",
                "",
                "&7Pokonaj go aby...",
                "&7Zdobyc &fcenne &7nagrody!",
                "",
                "&7Zycie&8: &c" + MathUtil.round(zombie.getHealth(), 2) + "HP",
                "&7Kordy bossa&8:", "&7x&8: &f" + zombie.getLocation().getBlockX() + " &7z&8: &f" + zombie.getLocation().getBlockZ()));
        bPlayerBoard.setAll(a.toArray(a.toArray(new String[0])));

    }
}
