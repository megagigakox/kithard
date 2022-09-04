package pl.kithard.proxy.util;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class BungeeUtil {

    private BungeeUtil() {

    }

    public static void sendCustomData(ProxiedPlayer player, String data1, String data2) {

        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(stream);
            out.writeUTF(data1);
            out.writeUTF(data2);

            player.getServer().sendData("BungeeCord", stream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
