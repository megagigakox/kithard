package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;

public class PlayerKillsVariable extends Variable {
    private final CorePlugin plugin;

    public PlayerKillsVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);
        int kills = 0;
        if (corePlayer != null) {
            kills = corePlayer.getKills();
        }

        return Integer.toString(kills);
    }
}
