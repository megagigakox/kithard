package pl.kithard.proxy.motd;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class MotdListener implements Listener {
    private final ProxyPlugin plugin;

    public MotdListener(ProxyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void onProxyPingEvent(ProxyPingEvent event) {
        MotdConfig motdConfig = this.plugin.getMotdConfig();
        ServerPing serverPing = event.getResponse();
        serverPing.setDescriptionComponent(
                new TextComponent(TextUtil.color(motdConfig.getFormattedMotd()))
        );

        int averagePing = 0;
        if (this.plugin.getProxy().getOnlineCount() != 0) {
            for (ProxiedPlayer player : this.plugin.getProxy().getPlayers()) {
                averagePing += player.getPing();
            }

            averagePing = averagePing / this.plugin.getProxy().getOnlineCount();
        }

        List<String> sample = new ArrayList<>();
        for (String s : motdConfig.sample) {
            sample.add(s.replace("{AVERAGE_PING}", String.valueOf(averagePing)));
        }
        ServerPing.PlayerInfo[] serverPings = new ServerPing.PlayerInfo[sample.size()];

        for (int i = 0; i < serverPings.length; i++) {
            serverPings[i] = new ServerPing.PlayerInfo(TextUtil.color(
                    sample.get(i)),
                    String.valueOf(i)
            );
        }

        serverPing.setPlayers(
                new ServerPing.Players(
                        2000,
                        this.plugin.getProxy().getOnlineCount(),
                        serverPings)
        );

        event.setResponse(serverPing);
    }
}
