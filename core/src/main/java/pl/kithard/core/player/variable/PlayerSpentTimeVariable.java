package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.api.util.TimeUtil;

public class PlayerSpentTimeVariable extends Variable {
    private final CorePlugin plugin;

    public PlayerSpentTimeVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        return TimeUtil.formatTimeMillis(corePlayer.getSpentTime());
    }
}
