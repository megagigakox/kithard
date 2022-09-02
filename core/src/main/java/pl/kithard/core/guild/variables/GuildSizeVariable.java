package pl.kithard.core.guild.variables;

import codecrafter47.bungeetablistplus.api.bukkit.Variable;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;

public class GuildSizeVariable extends Variable {
    private final CorePlugin plugin;

    public GuildSizeVariable(String name, CorePlugin plugin) {
        super(name);
        this.plugin = plugin;
    }

    public String getReplacement(Player player) {
        Guild g = this.plugin.getGuildCache().findByPlayer(player);
        if (g != null) {
            int size = g.getRegion().getSize() * 2;
            return size + "x" + size;
        }
        return "&fBrak.";
    }
}
