package pl.kithard.proxy.auth;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.util.PremiumUtil;
import pl.kithard.proxy.util.TextUtil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AuthListener implements Listener {

    private final static ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private final ProxyPlugin plugin;

    public AuthListener(ProxyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String name = connection.getName();

        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(name);
        if (authPlayer == null) {
            authPlayer = new AuthPlayer(name);
            event.registerIntent(plugin);
            AuthPlayer finalAuthPlayer = authPlayer;
            this.plugin.getProxy().getScheduler().runAsync(plugin, () -> {

                try {
                    finalAuthPlayer.setPremium(PremiumUtil.isPremium(name));
                    this.plugin.getAuthPlayerCache().add(finalAuthPlayer);
                } finally {
                    event.completeIntent(plugin);
                }
            });

            if (!authPlayer.isPremium() && !authPlayer.getName().equals(connection.getName())) {
                System.out.println("cancelled");
                event.setCancelled(true);
                event.setCancelReason(TextComponent.fromLegacyText(TextUtil.color("&cNieprawidlowy nick! Wejdz na serwer z nicku: &b" + authPlayer.getName())));
                return;
            }

            connection.setOnlineMode(authPlayer.isPremium());
        }
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(player.getName());

        if (!authPlayer.isPremium()) {
            player.sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(TextUtil.color(
                            !authPlayer.isRegistered()
                                    ? "&7Musisz się zarejestrować! &a/register <hasło> <hasło>"
                                    : "&7Musisz się zalogować! &a/login <hasło>"))
            );

            return;
        }

        authPlayer.setLogged(true);
        player.sendMessage(TextComponent.fromLegacyText(TextUtil.color("&aPomyślnie zalogowano z konta &2premium&a!")));
        player.sendMessage(TextComponent.fromLegacyText(TextUtil.color("&aTrwa dodawanie do kolejki...")));
        EXECUTOR_SERVICE.schedule(() -> sendCustomData(player, player.getUniqueId().toString(), "main"), 1L, TimeUnit.SECONDS);


    }

    public void sendCustomData(ProxiedPlayer player, String data1, String data2) {

        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF(data1);
            out.writeUTF(data2);

            System.out.println("wysłano");

            player.getServer().sendData("BungeeCord", stream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
