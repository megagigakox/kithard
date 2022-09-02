package pl.kithard.discord.bot.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.kithard.discord.bot.util.EmbedUtil;

import java.awt.*;
import java.util.Objects;

public class ButtonClickListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        Guild guild = event.getGuild();
        if (!event.getChannel().getId().equals("1013855721508585514")) {
            return;
        }

        if (!Objects.equals(event.getButton().getId(), "verify")) {
            return;
        }

        Role verified = guild.getRoleById(977889066190241833L);
        if (verified == null) {
            return;
        }

        Member member = event.getMember();
        if (!member.getRoles().contains(verified)) {
            guild.addRoleToMember(member, verified).queue();
            event.replyEmbeds(EmbedUtil.messageEmbed(
                    Color.decode("#39e75f"),
                    "Kithard - verify",
                    "Zostałeś pomyślnie zweryfikowany!",
                    ""
            )).setEphemeral(true).complete();
            return;
        }

        event.replyEmbeds(EmbedUtil.messageEmbed(
                Color.decode("#ff0000"),
                "Kithard - verify",
                "Jesteś już zweryfikowany!",
                ""
        )).setEphemeral(true).complete();
    }



}
