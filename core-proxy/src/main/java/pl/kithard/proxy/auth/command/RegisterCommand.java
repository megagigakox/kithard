package pl.kithard.proxy.auth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.util.BungeeUtil;
import pl.kithard.proxy.util.TextUtil;

public class RegisterCommand extends Command {

    private final ProxyPlugin plugin;

    public RegisterCommand(ProxyPlugin plugin) {
        super("register", "", "reg");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(player.getName());
        if (authPlayer.isRegistered()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cJestes juz zarejestrowany!")));
            return;
        }

        if (authPlayer.isPremium()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cJestes graczem premium!")));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/register (haslo) (haslo)")));
            return;
        }

        String password = args[0];
        String confirmPassword = args[1];

        if (!password.equals(confirmPassword)) {
            player.sendMessage(new TextComponent(TextUtil.color("&cHasla sie nie zgadzaja!")));
            return;
        }

        if (password.length() < 6) {
            player.sendMessage(new TextComponent(TextUtil.color("&cHaslo musi zawierac przynajmniej 6 znakow!")));
            return;
        }

        player.sendMessage(new TextComponent(TextUtil.color("&aPomyslnie zarejestrowano!")));
        authPlayer.setPassword(password);
        authPlayer.setRegistered(true);
        authPlayer.setLogged(true);
        authPlayer.setNeedSave(true);
        BungeeUtil.sendCustomData(player, player.getUniqueId().toString(), "main");
    }
}
