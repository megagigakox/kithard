package pl.kithard.queue.util;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public final class TitleUtil {

    private TitleUtil() {}

    public static void title(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title == null) {
            title = "";
        }

        if (subtitle == null) {
            subtitle = "";
        }

        CraftPlayer craftPlayer = (CraftPlayer) player;
        PacketPlayOutTitle packetTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTimes);
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + TextUtil.color(title) + "\"}");
        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetTitle);
        IChatBaseComponent chatSubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + TextUtil.color(subtitle) + "\"}");
        PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubtitle);
        craftPlayer.getHandle().playerConnection.sendPacket(packetSubtitle);
    }


}
