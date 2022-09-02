package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildAllyFireCommand {

    private final CorePlugin plugin;

    public GuildAllyFireCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g pvpsojusz",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.PVP_CHANGE)) {
            return;
        }

        guild.setAllyFire(!guild.isAllyFire());
        guild.setNeedSave(true);

        TextUtil.message(player, "&8[&3&l!&8] &7Zmieniono tryb pvp w sojuszu na: " + (guild.isAllyFire() ? "&awlaczony" : "&cwylaczony"));

    }
}
