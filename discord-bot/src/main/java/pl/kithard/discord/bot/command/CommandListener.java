package pl.kithard.discord.bot.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;
import pl.kithard.discord.bot.util.EmbedUtil;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


public class CommandListener extends ListenerAdapter {

    private final CommandCache commandCache;

    public CommandListener(CommandCache commandCache) {
        this.commandCache = commandCache;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        Member member = event.getMember();
        if (member == null) {
            return;
        }

        Optional<Command> optionalCommand = this.commandCache.findByName(args[0]);
        optionalCommand.ifPresent(command -> {

            CommandInfo commandInfo = command.getClass().getAnnotation(CommandInfo.class);
            if (!PermissionUtil.checkPermission(member, commandInfo.permission())) {
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(
                        EmbedUtil.messageEmbed(
                                Color.decode("#ff0000"),
                                "Błąd!",
                                "Nie posiadasz wystarczających uprawnień do wykonania tej komendy!",
                                ""))
                        .queue(message -> message.delete().queueAfter(5, TimeUnit.SECONDS));

                return;
            }

            command.execute(event, args);
        });
    }

}
