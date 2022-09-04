package pl.kithard.proxy.auth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.util.BungeeUtil;
import pl.kithard.proxy.util.TextUtil;

public class LoginCommand extends Command {

    private final ProxyPlugin plugin;

    public LoginCommand(ProxyPlugin plugin) {
        super("login", "", "l", "zaloguj");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(player.getName());
        if (authPlayer.isPremium()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cJestes graczem premium!")));
            return;
        }

        if (!authPlayer.isRegistered()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cNajpierw musisz sie zarejestrowac!")));
            return;
        }

        if (authPlayer.isLogged()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cJestes juz zalogowany!")));
            return;
        }

        if (args.length < 1) {
            player.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/login (haslo)")));
            return;
        }

        String password = args[0];
        if (!password.equals(authPlayer.getPassword())) {
            player.sendMessage(new TextComponent(TextUtil.color("&cPodane haslo jest nieprawidlowe.")));
            return;
        }

        player.sendMessage(new TextComponent(TextUtil.color("&aPomyslnie zalogowano!")));
        authPlayer.setLogged(true);
        BungeeUtil.sendCustomData(player, player.getUniqueId().toString(), "main");
    }
}
