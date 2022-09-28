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
public class    PrivateMessageCommand {

    private final CorePlugin plugin;

    public PrivateMessageCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "msg",
            aliases = {"privatemessage", "pm", "tell"},
            acceptsExceeded = true,
            playerOnly = true,
            completer = "online-players:500"
    )
    public void handle(Player player, CorePlayer corePlayer, String[] args) {
        if (args.length < 2) {
            TextUtil.correctUsage(player, "/msg (gracz) (wiadomosc)");
            return;
        }

        if (args[0].equalsIgnoreCase(player.getName())) {
            return;
        }

        CorePlayer targetCorePlayer = this.plugin.getCorePlayerCache().findByName(args[0]);
        if (targetCorePlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz &4nie istnieje &cw bazie danych!");
            return;
        }

        Player targetPlayer = targetCorePlayer.source();
        if (targetPlayer == null) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz jest aktualnie &4offline&c!");
            return;
        }

        if (targetCorePlayer.isIgnoredUuid(player.getUniqueId()) || targetCorePlayer.isDisabledSetting(PlayerSettings.PRIVATE_MESSAGES)) {
            TextUtil.message(player, "&8(&4&l!&8) &cTen gracz Cie ignoruje lub ma wylaczone prywatne wiadomosci!");
            return;
        }

        String message = ChatColor.stripColor(StringUtils.join(args, " ", 1, args.length));
        corePlayer.setReply(targetCorePlayer.getUuid());
        targetCorePlayer.setReply(corePlayer.getUuid());

        TextUtil.message(targetPlayer, "&3{NICK} &8-> &3Ja&8: &f{MESSAGE}"
                .replace("{NICK}", player.getName())
                .replace("{MESSAGE}", message));

        TextUtil.message(player, "&3Ja &8-> &3{NICK}&8: &f{MESSAGE}"
                .replace("{NICK}", targetPlayer.getName())
                .replace("{MESSAGE}", message));
    }
}
