package pl.kithard.core.player.variable;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

public class UniquePlayersVariable extends Variable {

    private final CorePlugin plugin;

    public UniquePlayersVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public String getReplacement(Player player) {
        return String.valueOf(this.plugin.getCorePlayerCache().getValues().size());
    }
}
