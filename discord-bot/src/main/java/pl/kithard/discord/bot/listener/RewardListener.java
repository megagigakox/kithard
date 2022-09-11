package pl.kithard.discord.bot.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class RewardListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel().getId().equals("1017852198614474792")) {
            if (event.getAuthor().isBot()) {
                return;
            }

            if (event.getMessage().getContentRaw().split(" ")[0].equalsIgnoreCase("!nagroda")) {
                return;
            }

            event.getMessage().delete().queue();
        }
    }
}
