package pl.kithard.proxy.auth.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.util.TextUtil;

public class AuthCommand extends Command {

    private final ProxyPlugin plugin;

    public AuthCommand(ProxyPlugin plugin) {
        super("auth", "auth.admin", "opalkaauth");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(new TextComponent(TextUtil.color("&8» &f/auth unregister (gracz) &8- &7Odrejestrowywuje gracza")));
            sender.sendMessage(new TextComponent(TextUtil.color("&8» &f/auth changepassword (gracz) (haslo) &8- &7Zmiana hasla gracza")));
            sender.sendMessage(new TextComponent(TextUtil.color("&8» &f/auth info (gracz) &8- &7Informacje o graczu")));
            return;
        }

        switch (args[0]) {

            case "unregister": {

                if (args.length < 2) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/auth unregister (gracz)")));
                    return;
                }

                AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(args[1]);
                if (authPlayer == null) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz nie istnieje w bazie danych!")));
                    return;
                }

                if (authPlayer.isPremium()) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz jest zarejestrowany jako &bpremium&c!")));
                    return;
                }

                if (!authPlayer.isRegistered()) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz nie jest zarejestrowany!")));
                    return;
                }

                authPlayer.setPassword("");
                authPlayer.setRegistered(false);

                this.plugin.getProxy().getScheduler().runAsync(plugin, () -> this.plugin.getMongoService().save(authPlayer));

                sender.sendMessage(new TextComponent(TextUtil.color("&aPomyslnie odrejestrowano gracza: &b" + authPlayer.getName() + "&a!")));

                ProxiedPlayer proxiedPlayer = this.plugin.getProxy().getPlayer(authPlayer.getName());
                if (proxiedPlayer != null) {
                    proxiedPlayer.disconnect(new TextComponent(TextUtil.color("&cTwoje konto zostalo odrejestrowane przez: &b" + sender.getName() + "&c!")));
                }

                return;
            }

            case "changepass":
            case "changepassword": {

                if (args.length < 3) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/auth changepassword (gracz) (haslo)")));
                    return;
                }

                AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(args[1]);
                if (authPlayer == null) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz nie istnieje w bazie danych!")));
                    return;
                }

                if (authPlayer.isPremium()) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz jest zarejestrowany jako &bpremium&c!")));
                    return;
                }

                if (!authPlayer.isRegistered()) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz nie jest zarejestrowany!")));
                    return;
                }

                authPlayer.setPassword(args[2]);
                this.plugin.getProxy().getScheduler().runAsync(plugin, () -> this.plugin.getMongoService().save(authPlayer));
                sender.sendMessage(new TextComponent(TextUtil.color("&aPomyslnie ustawiono nowe haslo gracza: &b" + authPlayer.getName() + "&a!")));
                return;
            }

            case "info": {

                if (args.length < 2) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cPoprawne uzycie: &b/auth info (gracz)")));
                    return;
                }

                AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(args[1]);
                if (authPlayer == null) {
                    sender.sendMessage(new TextComponent(TextUtil.color("&cTen gracz nie istnieje w bazie danych!")));
                    return;
                }

                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7Nick&8: &f" + authPlayer.getName())));
                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7IP&8: &f" + authPlayer.getIp())));
                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7Data rejestracji&8: &f" + TimeUtil.formatTimeMillisToDate(authPlayer.getFirstJoinTime()))));
                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7Premium&8: &f" + authPlayer.isPremium())));
                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7Zarejestrowany&8: &f" + authPlayer.isRegistered())));
                sender.sendMessage(new TextComponent(TextUtil.color("&8» &7Zalogowany&8: &f" + authPlayer.isLogged())));


            }


        }


    }
}