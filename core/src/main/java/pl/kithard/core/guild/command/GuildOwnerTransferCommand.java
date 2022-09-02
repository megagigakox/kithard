package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildOwnerTransferCommand {

    private final CorePlugin plugin;

    public GuildOwnerTransferCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g lider",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/g lider (gracz)");
            return;
        }

        if (!guild.isOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie jestes liderem gildii!");
            return;
        }

        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);

        if (args[0].equalsIgnoreCase(player.getName())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie mozesz oddac lidera samemu sobie!");
            return;
        }

        if (corePlayer == null) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        if (!guild.isMember(corePlayer.getUuid())) {
            TextUtil.message(player, "&8[&4&l!&8] &cTen gracz nie jest w twojej gildii!");
            return;
        }

        guild.setOwner(corePlayer.getUuid());
        guild.setNeedSave(true);
        TextUtil.message(player, "&8[&2&l!&8] &aPomyslnie przekazano lidera graczowi &2" + corePlayer.getName());
    }
}
