package pl.kithard.proxy.motd;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.kithard.proxy.ProxyPlugin;
import pl.kithard.proxy.util.TextUtil;

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

        ServerPing.PlayerInfo[] serverPings = new ServerPing.PlayerInfo[motdConfig.sample.size()];
        List<String> sample = this.plugin.getMotdConfig().sample;

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
