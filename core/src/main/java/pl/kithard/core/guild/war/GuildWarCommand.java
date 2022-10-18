package pl.kithard.core.guild.war;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildWarCommand {

    private final CorePlugin plugin;

    public GuildWarCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g wojna",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {

        if (!guild.isDeputyOrOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8(&4&l!&8) &cTylko lider lub zastepca moze wypowiadac wojny!");
            return;
        }

        new GuildWarGui(this.plugin).open(player, guild);

    }
}
