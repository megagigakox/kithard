package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;

@FunnyComponent
public class GuildWarehouseCommand {

    private final CorePlugin plugin;

    public GuildWarehouseCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g magazyn",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {
        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.WAREHOUSE_ACCESS)) {
            return;
        }

        GuildLog guildLog = guild.addLog(new GuildLog(guild.getTag(), GuildLogType.OTHER, "&f" + player.getName() + " &7otworzyl magazyn gildyjny."));
        this.plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(this.plugin, () -> this.plugin.getGuildRepository().insertLog(guildLog));
        guild.openWarehouse(player);
    }
}
