package pl.kithard.queue.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TransferUtil {

    private TransferUtil() {
    }

    public static void transfer(Player player, String server, JavaPlugin plugin) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(arrayOutputStream);

        try {

            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);

        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(
                plugin,
                "BungeeCord",
                arrayOutputStream.toByteArray()
        );
    }
}