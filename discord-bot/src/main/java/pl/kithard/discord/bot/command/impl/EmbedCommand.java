package pl.kithard.discord.bot.command.impl;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.kithard.discord.bot.command.Command;
import pl.kithard.discord.bot.command.CommandInfo;
import pl.kithard.discord.bot.util.EmbedUtil;

import java.awt.*;

@CommandInfo(name = "!embed", permission = Permission.ADMINISTRATOR)
public class EmbedCommand extends Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 1) {
            return;
        }

        MessageEmbed embed = EmbedUtil.messageEmbed(
                Color.decode(args[1]),
                args[2],
                args[3],
                args[4]);

        event.getMessage().delete().queue();
        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}
