package pl.kithard.discord.bot.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.kithard.discord.bot.DiscordBot;
import pl.kithard.discord.bot.command.Command;
import pl.kithard.discord.bot.command.CommandInfo;

import java.awt.*;

@CommandInfo(name = "!nagroda", permission = Permission.UNKNOWN)
public class RewardCommand extends Command {
    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (event.getChannel().getId().equals("1017852198614474792")) {
            event.getMessage().delete().queue();
            if (args.length < 2) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.decode("#ffffff"))
                        .setTitle("Nagroda")
                        .setDescription("Blad! Poprawne uzycie !nagroda <nick z minecrafta>!");
                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                return;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setColor(Color.decode("#ffffff"))
                    .setTitle("Nagroda")
                    .setDescription("Pomyslnie nadano nagrode na nick: " + args[1]);

            DiscordBot.getRedisService().set("rewardsToAssign", args[1], args[1]);
            MessageEmbed messageEmbed = embedBuilder.build();
            Role role = event.getGuild().getRoleById(1018125986832846908L);
            if (role == null) {
                System.out.println("role == null");
                return;
            }
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            event.getChannel().sendMessageEmbeds(messageEmbed).queue();
        }
    }
}
