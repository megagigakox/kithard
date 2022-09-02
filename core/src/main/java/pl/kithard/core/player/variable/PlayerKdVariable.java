package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

public class PlayerKdVariable extends Variable {
    private final CorePlugin plugin;

    public PlayerKdVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(final Player player) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        return (corePlayer == null) ? "" : String.valueOf(corePlayer.getKd());
    }
}
