package pl.kithard.discord.bot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.kithard.discord.bot.util.EmbedUtil;

import java.awt.*;

public class MemberJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setColor(Color.decode("#ffffff"))
                .setTitle("Powitajmy nowego użytkownika!")
                .setDescription(
                        "Witaj <@" + event.getMember().getId() + "> na oficjalnym discordzie serwera **KitHard.pl** \n" +
                        " Jesteś **" + event.getGuild().getMemberCount() + "** osobą na naszym serwerze discord! \n" +
                        " Mamy nadzieję, że zostaniesz z nami na dłużej!"
                )
                .setImage("https://i.imgur.com/2BdqKei.jpg");

        TextChannel textChannel = event.getGuild().getTextChannelById(1013861113605140530L);
        if (textChannel == null) {
            return;
        }

        textChannel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

}
