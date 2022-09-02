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
import pl.kithard.core.util.TimeUtil;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PlayerCommandPreprocessListener implements Listener {

    private final static String[] DISALLOWED_COMMANDS = {"me",
            "src/src/main/java/pl",
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
            "bukkit:help"
    };

    private final CorePlugin plugin;

    public PlayerCommandPreprocessListener(CorePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
        disableTabulation();
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage().toLowerCase(Locale.ROOT);
        String[] splitMessage = message.split(" ");

        for (String it : DISALLOWED_COMMANDS) {
            if (splitMessage[0].equalsIgnoreCase("/" + it)) {
                event.setCancelled(true);
                TextUtil.message(event.getPlayer(), "&8[&4&l!&8] &cTa komenda zostala zablokowana!");
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onCommandCooldown(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        CorePlayer corePlayer = this.plugin.getCorePlayerCache().findByPlayer(player);

        if (corePlayer.getCooldown().getCommandsDelay() > System.currentTimeMillis() && !player.hasPermission("kithard.commands.delay.bypass")) {
            TextUtil.message(player,
                    "&8[&4&l!&8] &cNastepnym razem komende możesz użyc za &4" +
                            TimeUtil.formatTimeMillis(corePlayer.getCooldown().getCommandsDelay() - System.currentTimeMillis()));
            event.setCancelled(true);
            return;
        }

        corePlayer.getCooldown().setCommandsDelay(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1));
    }

    private void disableTabulation() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {
             @Override
             public void onPacketReceiving(PacketEvent event) {
                 if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                     try {
                         PacketContainer packet = event.getPacket();
                         String message = (packet.getSpecificModifier(String.class).read(0)).toLowerCase();

                            for (String it : DISALLOWED_COMMANDS) {
                                if (message.startsWith("/" + it)) {
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
