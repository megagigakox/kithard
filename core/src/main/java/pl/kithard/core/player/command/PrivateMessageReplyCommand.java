package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.kithard.core.player.settings.PlayerSettings;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class PrivateMessageReplyCommand {

    private final CorePlugin plugin;

    public PrivateMessageReplyCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "reply",
            aliases = "r",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/reply (wiadomosc)");
            return;
        }

        if (corePlayer.getReply() == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie masz komu odpisac!");
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByUuid(corePlayer.getReply());
        if (targetCorePlayer == null || targetCorePlayer.source() == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie masz komu odpisac!");
            return;
        }

        if (targetCorePlayer.isDisabledSetting(PlayerSettings.PRIVATE_MESSAGES) || targetCorePlayer.isIgnoredUuid(player.getUniqueId())) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz Cie ignoruje lub ma wylaczone prywatne wiadomosci!");
            return;
        }

        String message = ChatColor.stripColor(StringUtils.join(args, " ", 0, args.length));

        TextUtil.message(targetCorePlayer.source(), "&3{NICK} &8-> &3Ja&8: &f{MESSAGE}"
                .replace("{NICK}", player.getName())
                .replace("{MESSAGE}", message));

        TextUtil.message(player, "&3Ja &8-> &3{NICK}&8: &f{MESSAGE}"
                .replace("{NICK}", targetCorePlayer.getName())
                .replace("{MESSAGE}", message));

    }
}
