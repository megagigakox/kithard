package pl.kithard.discord.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public final class EmbedUtil {

    private EmbedUtil() {}

    public static MessageEmbed messageEmbed(Color color, String title, String description, String footer) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(color);
        embedBuilder.setTitle(title);
        embedBuilder.setDescription(description);
        embedBuilder.setFooter(footer);
        embedBuilder.setTimestamp(Instant.now());
        return embedBuilder.build();
    }

}
