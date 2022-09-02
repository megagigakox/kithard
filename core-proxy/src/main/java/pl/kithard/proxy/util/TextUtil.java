package pl.kithard.proxy.util;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String color(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> text) {
        List<String> colored = new ArrayList<>();
        text.forEach(s -> colored.add(color(s)));

        return colored;
    }


}
