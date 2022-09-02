package pl.kithard.core.util;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class TextUtil {

    private TextUtil() {}

    public static String color(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Component component(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        return Component.text(ChatColor.translateAlternateColorCodes('&', text));
    }

    public static List<Component> component(List<String> text) {
        List<Component> colored = new ArrayList<>();
        text.forEach(s -> colored.add(component(s)));

        return colored;
    }

    public static List<String> color(List<String> text) {
        List<String> colored = new ArrayList<>();
        text.forEach(s -> colored.add(color(s)));

        return colored;
    }

    public static void message(CommandSender commandSender, String text) {
        commandSender.sendMessage(color(text));
    }

    public static void message(CommandSender commandSender, List<String> text) {
        text.forEach(value -> message(commandSender, value));
    }

    public static void correctUsage(CommandSender commandSender, String usage) {
        message(commandSender, "&8[&3&l!&8] &7Poprawne użycie: &b{USAGE}"
                .replace("{USAGE}", usage));
    }

    public static void insufficientPermission(CommandSender commandSender, String permission) {
        message(commandSender, "&8[&4&l!&8] &cNie posiadasz uprawnien do wykonania tej czynnosci! &4({PERMISSION})"
                .replace("{PERMISSION}", permission));
    }

    public static void sendEmptyMessage(Player player, int i) {
        IntStream.range(0, i).forEach(it -> TextUtil.message(player, ""));
    }

    public static void announce(String text) {
        Bukkit.getOnlinePlayers().forEach(value -> value.sendMessage(color(text)));
    }

    public static String progressBar(long current, long max, int bars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = current / (float) max;
        int progressBars = (int) (bars * percent);
        int leftOver = bars - progressBars;
        StringBuilder builder = new StringBuilder();
        if (current > max) {
            builder.append(completedColor);
            for (int i = 0; i < bars; ++i) {
                builder.append(symbol);
            }
            return builder.toString();
        }
        builder.append(completedColor.toString());
        for (int i = 0; i < progressBars; ++i) {
            builder.append(symbol);
        }
        builder.append(notCompletedColor.toString());
        for (int i = 0; i < leftOver; ++i) {
            builder.append(symbol);
        }

        return builder.toString();
    }


}