package pl.kithard.proxy.auth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.util.TextUtil;

public class ChangePasswordCommand extends Command {

    private final ProxyPlugin plugin;

    public ChangePasswordCommand(ProxyPlugin plugin) {
        super("changepassword", "", "zmienhaslo", "changepass");
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

        if (!authPlayer.isLogged()) {
            player.sendMessage(new TextComponent(TextUtil.color("&cNajpierw musisz sie zalogowac!")));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/changepassword (stare-haslo) (nowe-haslo)")));
            return;
        }

        String oldPassword = args[0];
        String newPassword = args[1];

        if (!oldPassword.equals(authPlayer.getPassword())) {
            player.sendMessage(new TextComponent(TextUtil.color("&cStare haslo jest nieprawidlowe!")));
            return;
        }

        if (newPassword.length() < 6) {
            player.sendMessage(new TextComponent(TextUtil.color("&cHaslo musi zawierac przynajmniej 6 znakow!")));
            return;
        }

        authPlayer.setPassword(newPassword);
        this.plugin.getProxy().getScheduler().runAsync(plugin, () -> this.plugin.getMongoService().save(authPlayer));
        player.sendMessage(new TextComponent(TextUtil.color("&aPomyslnie ustawiono nowe haslo!")));
    }
}
