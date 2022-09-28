package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.util.TextUtil;

import java.util.concurrent.TimeUnit;

@FunnyComponent
public class GuildProlongCommand {

    private final CorePlugin plugin;

    public GuildProlongCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g przedluz",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_PROLONG)) {
            return;
        }

        if (guild.getExpireTime() + TimeUnit.DAYS.toMillis(1) > System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)) {
            TextUtil.message(player, "&8(&4&l!&8) &cGildia jest przedluzona na maksymalny okres &47 dni&c!");
            return;
        }

        if (player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), 64)) {
            player.getInventory().removeItem(new ItemStack(Material.EMERALD_BLOCK, 64));
            guild.setExpireTime(guild.getExpireTime() + TimeUnit.DAYS.toMillis(1));

            GuildLog guildLog = new GuildLog(
                    guild.getTag(),
                    GuildLogType.GUILD_PROLONG,
                    "&f" + player.getName() + " &7przedluzyl waznosc gildii."
            );
            guild.addLog(guildLog);
            this.plugin.getServer()
                    .getScheduler()
                    .runTaskAsynchronously(this.plugin, () -> this.plugin.getGuildRepository().insertLog(guildLog));

            guild.setNeedSave(true);
            TextUtil.message(player, "&8(&3&l!&8) &7Przedluzylesz waznosc gildii o &f1 dzien&7!");
            return;
        }

        TextUtil.message(player, "&8(&4&l!&8) &cNie posiadasz &464x blokow emeraldow &cdo przedluzenia gildii!");
    }
}
