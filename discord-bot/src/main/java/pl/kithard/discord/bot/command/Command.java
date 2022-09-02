package pl.kithard.discord.bot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    public abstract void execute(MessageReceivedEvent event, String[] args);

}
