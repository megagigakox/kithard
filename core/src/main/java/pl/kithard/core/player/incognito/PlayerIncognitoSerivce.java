package pl.kithard.core.player.incognito;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.RandomStringUtil;
import pl.kithard.core.player.CorePlayer;
import pl.kithard.core.util.reflection.FieldAccessor;
import pl.kithard.core.util.reflection.Reflection;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerIncognitoSerivce {

    private static final String SKIN =
            "eyJ0aW1lc3RhbXAiOjE1MjkwNzU2NzYxNjMsInByb2ZpbGVJZCI6ImMxZWQ5N2Q0ZDE2NzQyYzI5OGI1ODFiZmRiODhhMjFmIiwicHJvZmlsZU5hbWUiOiJ5b2xvX21hdGlzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81MmRmMmEzNTYwYzIzNDQwNzcxYWJkNjI1NDM4YTU1NjM1NjVlNzcxMTM0MDFmOWI2OWExNjk3OGU1NDQ5YWFhIn19fQ==";
    private static final String SIGNATURE =
            "QruOyKsAYuSRXl0CthLMAoQC6bp2WsKtcROQQd3NBChH6RVdwz7zNPp15uwlNaYU1ijMHxcF/rHcb98B9nQ0L81Im1JK4KQJJMrgLfv5WE4PtkQH1McDavvi0H1npSQlH1NTMXRw+5sa/DRC8nH1M9JxJy64HrcGTLGTuuwc5RW7YqfxCfqtdpSsl7ZBRxDHoNW1xGeG0HemcN7Atyuma4aiQdEO8d3GOwwen1dovFawtNZMU180apDIyYZryBBZh6JWdAFpm8avQG7h+ZIZuyjDKGkn6yyZR+xcv29lsbudS8ylx3WpxsszOVHtUfi2p9AhTOQK8INw1Ulft3xgBbU7E2dMcgx/1PGIvyZuzXuRFtl4qJIozEZQNJ4OZC3tgjpBJiT3rvdJ95hjmHi8z1EClUXsL+xi12w81DosrfMy9FpYjx6thVOggQycEUiruOE78WWTQVH/Lts76wYnOWdti9IOKUNqFckkR9D+cCK1sBT5PZd2Y5Tb15KvvjGPR79PW7CNbUuKvBGn2/THeUnEAx515EMe+lg2D3GyGdASBoPiEhDdBSjWYNGTw5kJuBfDIbsPBJInkDRV975limaj5TSHwWVPuWDoH7KMq/OPjxkuJ8q/yB04k+ZKxgapofgHGsWnHXNW1YGqrnUfkYhcV0ffjndOjPtvrHVRkn4=";

    private final CorePlugin plugin;

    public PlayerIncognitoSerivce(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void changeSkin(CorePlayer corePlayer) {
        Player player = corePlayer.source();
        if (player == null) {
            return;
        }

        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();
        AtomicReference<GameProfile> gameProfile = new AtomicReference<>();

        PlayerIncognito playerIncognito = corePlayer.getPlayerIncognito();

        if (playerIncognito.isSkinIncognito()) {

            if (playerIncognito.getCachedGameProfile() == null) {
                corePlayer.getPlayerIncognito().setCachedGameProfile(craftPlayer.getProfile());
            }

            gameProfile.set(new GameProfile(player.getUniqueId(), player.getName()));
            gameProfile.get().getProperties().removeAll("textures");
            gameProfile.get().getProperties().put("textures", new Property("textures", SKIN, SIGNATURE));
        } else {
            gameProfile.set(corePlayer.getPlayerIncognito().getCachedGameProfile());
        }

        FieldAccessor<GameProfile> bH = Reflection.getField(EntityPlayer.class, "bH", GameProfile.class);
        if (gameProfile.get() != null) {
            bH.set(entityPlayer, gameProfile.get());
        }

        sendPacketExcept(player,
                new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityPlayer),
                new PacketPlayOutEntityDestroy(craftPlayer.getEntityId())
        );

        Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin,
                () -> {
                    sendPacketExcept(
                            player,
                            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle()),
                            new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle())
                    );
                }, 5);
    }

    public static void sendPacketExcept(Player except, Packet<?>... packets) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.equals(except)) {
                continue;
            }
            for (Packet<?> packet : packets) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

}
