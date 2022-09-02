package pl.kithard.discord.bot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import pl.kithard.discord.bot.command.Command;
import pl.kithard.discord.bot.command.CommandInfo;

import java.awt.*;

@CommandInfo(name = "!verify", permission = Permission.ADMINISTRATOR)
public class VerifyCommand extends Command {

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Kithard - verify")
                .setDescription("Pamiętaj aby przeczytać regulamin i go przestrzegać!")
                .setImage("https://i.imgur.com/uO5swjp.jpeg")
                .setColor(Color.decode("#00ff00"));

        Button button = Button.success("verify", "Kliknij aby się zweryfikować");
        event.getMessage().delete().queue();
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(button).queue();
    }
}
