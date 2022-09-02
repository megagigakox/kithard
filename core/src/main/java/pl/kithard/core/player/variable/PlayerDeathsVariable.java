package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

public class PlayerDeathsVariable extends Variable {
    private final CorePlugin plugin;

    public PlayerDeathsVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        int deaths = 0;
        if (corePlayer != null) {
            deaths = corePlayer.getDeaths();
        }
        return Integer.toString(deaths);
    }
}
