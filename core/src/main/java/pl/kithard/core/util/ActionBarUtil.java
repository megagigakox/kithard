package pl.kithard.core.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class ActionBarUtil {

    private ActionBarUtil() {}

    public static void actionBar(Player player, String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent
                .ChatSerializer.a("{\"text\": \"" + TextUtil.color(message) + "\"}");
        PacketPlayOutChat playOutChat = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(playOutChat);
    }

}
