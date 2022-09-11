package pl.kithard.core.player.chat.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.settings.ServerSettings;
import pl.kithard.core.settings.ServerSettingsType;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class ChatManageCommand {

    private final CorePlugin plugin;

    public ChatManageCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "chat",
            aliases = {"cm", "chatmanage"},
            permission = "kithard.commands.chatmanage",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender, String[] args) {

        if (args.length < 1) {
            TextUtil.correctUsage(sender, "/chat (on/off/clear)");
            return;
        }

        ServerSettings serverSettings = this.plugin.getServerSettings();


        switch (args[0]) {

            case "on": {

                if (serverSettings.isEnabled(ServerSettingsType.CHAT)) {
                    TextUtil.message(sender, "&8[&4&l!&8] &cChat jest już wlaczony!");
                    return;
                }

                serverSettings.addEnabledSetting(ServerSettingsType.CHAT);
                this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getServerSettingsService().save(serverSettings));

                for (Player player : Bukkit.getOnlinePlayers()) {

                    TextUtil.sendEmptyMessage(player, 50);
                    TextUtil.message(player, "&8[&3&l!&8] &7Chat serwerowy zostal &awlaczony &7przez: &b" + sender.getName());
                }

                return;
            }

            case "off": {

                if (!serverSettings.isEnabled(ServerSettingsType.CHAT)) {
                    TextUtil.message(sender, "&8[&4&l!&8] &cChat jest już wylaczony!");
                    return;
                }

                serverSettings.removeEnabledSetting(ServerSettingsType.CHAT);
                this.plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> this.plugin.getServerSettingsService().save(serverSettings));

                for (Player player : Bukkit.getOnlinePlayers()) {

                    TextUtil.sendEmptyMessage(player, 50);
                    TextUtil.message(player, "&8[&3&l!&8] &7Chat serwerowy zostal &cwylaczony &7przez: &b" + sender.getName());
                }

                return;
            }

            case "cc":
            case "c":
            case "clear": {

                for (Player player : Bukkit.getOnlinePlayers()) {

                    TextUtil.sendEmptyMessage(player, 500);
                    TextUtil.message(player, "&8[&3&l!&8] &7Chat serwerowy zostal &fwyczyszczony &7przez: &b" + sender.getName());
                }

                return;
            }

            default: TextUtil.correctUsage(sender, "/chat (on/off/clear)");

        }

    }

}
