package pl.kithard.discord.bot.command.impl;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.kithard.discord.bot.command.Command;
import pl.kithard.discord.bot.command.CommandInfo;

@CommandInfo(name = "!clear", permission = Permission.ADMINISTRATOR)
public class ClearCommand extends Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length < 1) {
            return;
        }

        TextChannel textChannel = event.getChannel().asTextChannel();
        textChannel.deleteMessages(textChannel.getHistory()
                .retrievePast(Integer.parseInt(args[1]))
                .complete())
                .queue();
    }
}
