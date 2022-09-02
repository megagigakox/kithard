package pl.kithard.core.guild.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class GuildKdVariable extends Variable {
    private final CorePlugin plugin;

    public GuildKdVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        Guild g = this.plugin.getGuildCache().findByPlayer(player);
        return g == null ? "&fBrak." : String.valueOf(g.getKd());
    }
}
