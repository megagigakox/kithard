package pl.kithard.proxy.auth.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.auth.AuthPlayer;
import pl.kithard.proxy.util.BungeeUtil;
import pl.kithard.proxy.mojang.MojangUtil;
import pl.kithard.proxy.util.TextUtil;

import java.util.concurrent.TimeUnit;

public class AuthListener implements Listener {

    private final ProxyPlugin plugin;

    public AuthListener(ProxyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String name = connection.getName();

        event.registerIntent(plugin);
        this.plugin.getProxy().getScheduler().runAsync(plugin, () -> {

            if (MojangUtil.isLimited()) {
                event.setCancelReason(TextUtil.component("&CSprobuj ponownie za chwile."));
                event.setCancelled(true);
                event.completeIntent(plugin);
                return;
            }

            if (this.plugin.getProxy().getPlayer(name) != null) {
                event.setCancelReason(TextUtil.component("&cTen gracz jest juz online!"));
                event.setCancelled(true);
                event.completeIntent(plugin);
                return;
            }

            if (name.length() < 3 || name.length() > 16) {
                event.setCancelReason(TextUtil.component("&cNieprawidlowa dlugosc nicku!"));
                event.setCancelled(true);
                event.completeIntent(plugin);
                return;
            }

            AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(name);
            if (authPlayer == null) {
                if (this.plugin.getAuthPlayerCache().hasMaxAccountsPerIP(connection.getAddress().getAddress().getHostAddress())) {
                    event.setCancelReason(TextUtil.component("&cOsiagnales limit kont na tym ip!"));
                    event.setCancelled(true);
                    event.completeIntent(plugin);
                    return;
                }
                authPlayer = this.plugin.getAuthPlayerCache().create(name);
                authPlayer.setPremium(MojangUtil.fetchStatus(name));
                authPlayer.setFirstJoinTime(System.currentTimeMillis());
                authPlayer.setIp(connection.getAddress().getAddress().getHostAddress());
                this.plugin.getAuthPlayerRepository().insert(authPlayer);
            }

            if (!authPlayer.isPremium() && !authPlayer.getName().equals(connection.getName())) {
                event.setCancelled(true);
                event.setCancelReason(TextUtil.component("&cNieprawidlowy nick! Wejdz na serwer z nicku: &b" + authPlayer.getName()));
                event.completeIntent(plugin);
                return;
            }

            if (authPlayer.getJoinCooldown() > System.currentTimeMillis()) {
                event.setCancelled(true);
                event.setCancelReason(TextUtil.component("&cKolejny raz bedziesz mogl sie polaczyc za: &b" + TimeUtil.formatTimeMillis(authPlayer.getJoinCooldown() - System.currentTimeMillis())));
                event.completeIntent(plugin);
                return;
            }

            connection.setOnlineMode(authPlayer.isPremium());
            event.completeIntent(plugin);
        });
    }

    @EventHandler
    public void onPostLogin(ServerConnectedEvent event) {
        if (!event.getServer().getInfo().getName().equals("lobby")) {
            return;
        }

        ProxiedPlayer player = event.getPlayer();
        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(player.getName());
        authPlayer.setJoinCooldown(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));

        if (authPlayer.getIp() == null || !authPlayer.getIp().equals(player.getAddress().getAddress().getHostAddress())) {
            authPlayer.setIp(player.getAddress().getAddress().getHostAddress());
            authPlayer.setNeedSave(true);
        }

        if (!authPlayer.isPremium()) {
            player.sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextUtil.component(!authPlayer.isRegistered()
                            ? "&7Musisz sie zarejestrowac! &b/register (haslo) (haslo)"
                            : "&7Musisz sie zalogowac! &b/login (haslo)"
                    ));

            player.sendMessage(TextUtil.component(
                    !authPlayer.isRegistered()
                            ? "&7Musisz sie zarejestrowac! &b/register (haslo) (haslo)"
                            : "&7Musisz sie zalogowac! &b/login (haslo)"
                    ));
            return;
        }

        authPlayer.setLogged(true);
        player.sendMessage(TextUtil.component("&aPomyslnie zalogowano z konta &bpremium&a!"));
        player.sendMessage(TextUtil.component("&aTrwa dodawanie do kolejki..."));
        ProxyPlugin.EXECUTOR_SERVICE.schedule(
                () -> BungeeUtil.sendCustomData(player, player.getUniqueId().toString(), "main"),
                2500L,
                TimeUnit.MILLISECONDS
        );
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        AuthPlayer authPlayer = this.plugin.getAuthPlayerCache().findByName(event.getPlayer().getName());
        authPlayer.setLogged(false);
    }


}
