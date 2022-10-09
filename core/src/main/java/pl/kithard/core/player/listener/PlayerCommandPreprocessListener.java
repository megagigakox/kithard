package pl.kithard.core.player.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.api.util.TimeUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PlayerCommandPreprocessListener implements Listener {

    private final static String[] DISALLOWED_COMMANDS = {
            "me",
            "minecraft:me",
            "pl",
            "plugins",
            "about",
            "ver",
            "version",
            "icanhasbukkit",
            "?",
            "bukkit:pl",
            "bukkit:plugins",
            "bukkit:ver",
            "bukkit:version",
            "bukkit:about",
            "bukkit:?",
            "bukkit:help",
            "/calc",
            "/eval",
            "/evaluate",
            "/calculate",
            "/to"
    };

    private final CorePlugin plugin;

    public PlayerCommandPreprocessListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        disableTabulation();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage().toLowerCase(Locale.ROOT);
        String[] splitMessage = message.split(" ");
        Player player = event.getPlayer();

        if (this.plugin.getServerSettings().getFreeze() > System.currentTimeMillis() && !player.hasPermission("kithard.freeze.bypass")) {
            TextUtil.message(player, "&8(&4&l!&8) &cNie mozesz uzywac komend podczas zamrozenia!");
            event.setCancelled(true);
            return;
        }

        for (String it : DISALLOWED_COMMANDS) {
            if (splitMessage[0].equalsIgnoreCase("/" + it)) {
                event.setCancelled(true);
                TextUtil.message(player, "&8(&3&l!&8) &7Ta komenda niestety &3nie istnieje&7! Aby sprawdzic &bnajpotrzebniejsze &7komendy na serwerze uzyj&8: /&fpomoc");
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCommandCooldown(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        if (corePlayer.getCooldown().getCommandsCooldown() > System.currentTimeMillis() && !player.hasPermission("kithard.commands.delay.bypass")) {
            TextUtil.message(player,
                    "&8(&4&l!&8) &cNastepnym razem komende mozesz uzyc za &4" +
                            TimeUtil.formatTimeMillis(corePlayer.getCooldown().getCommandsCooldown() - System.currentTimeMillis()));
            event.setCancelled(true);
            return;
        }

        corePlayer.getCooldown().setCommandsCooldown(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
    }

    private void disableTabulation() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {
             @Override
             public void onPacketReceiving(PacketEvent event) {
                 if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                     try {
                         PacketContainer packet = event.getPacket();
                         String message = packet.getSpecificModifier(String.class).read(0).toLowerCase();

                            for (String it : DISALLOWED_COMMANDS) {
                                if (message.startsWith("/" + it) || message.equals("/")) {
                                    event.setCancelled(true);
                                    break;
                                }
                            }

                     } catch (FieldAccessException exception) {
                         exception.printStackTrace();
                     }
                 }
             }
        });

    }

}
