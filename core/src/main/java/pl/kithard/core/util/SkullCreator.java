package pl.kithard.core.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.triumphteam.gui.components.util.SkullUtil;
import dev.triumphteam.gui.components.util.VersionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;


public final class SkullCreator {

    private SkullCreator() {}

    private static final Field PROFILE_FIELD;

    static {
        Field field;
        try {
            SkullMeta skullMeta = (SkullMeta)SkullUtil.skull().getItemMeta();
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
        } catch (NoSuchFieldException var2) {
            var2.printStackTrace();
            field = null;
        }

        PROFILE_FIELD = field;

    }

    /**
     * Creates a player skull, should work in both legacy and new Bukkit APIs.
     */
    public static ItemStack createSkull() {
        return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
    }

    public static ItemStack itemFromUuid(UUID id) {
        return itemWithUuid(createSkull(), id);
    }

    public static ItemStack itemFromBase64(String base64) {
        return itemWithBase64(createSkull(), base64);
    }

    public static ItemStack itemWithUuid(ItemStack item, UUID id) {
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwner(Bukkit.getOfflinePlayer(id).getName());

        item.setItemMeta(skullMeta);
        return item;
    }


    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            PROFILE_FIELD.set(skullMeta, profile);
        } catch (IllegalAccessException | IllegalArgumentException var6) {
            var6.printStackTrace();
        }

        item.setItemMeta(skullMeta);
        return item;
    }


}