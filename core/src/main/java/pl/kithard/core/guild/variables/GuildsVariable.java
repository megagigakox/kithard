package pl.kithard.core.guild.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;

public class GuildsVariable extends Variable {

    private final CorePlugin plugin;

    public GuildsVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    @Override
    public String getReplacement(Player player) {
        return String.valueOf(this.plugin.getGuildCache().getValues().size());
    }
}
