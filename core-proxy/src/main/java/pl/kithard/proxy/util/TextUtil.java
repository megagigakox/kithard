package pl.kithard.proxy.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public final class TextUtil {

    private TextUtil() {}

    public static String color(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static BaseComponent[] component(String text) {
        if (text == null || text.isEmpty()) {
            return TextComponent.fromLegacyText("");
        }

        return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text));
    }

    public static List<String> color(List<String> text) {
        List<String> colored = new ArrayList<>();
        text.forEach(s -> colored.add(color(s)));

        return colored;
    }


}
