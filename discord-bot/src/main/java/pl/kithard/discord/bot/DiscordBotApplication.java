package pl.kithard.discord.bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.*;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.api.reward.RewardRepository;
import pl.kithard.core.api.util.TimeUtil;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class DiscordBotApplication {

    private final DatabaseService databaseService;
    private final RewardRepository rewardRepository;

    public DiscordBotApplication() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.databaseService = new DatabaseService("mysql.titanaxe.com",3306, "srv235179", "srv235179", "CbccNpZ8");
        this.rewardRepository = new RewardRepository(this.databaseService);
        this.rewardRepository.prepareTable();

        DiscordApi discordApi = new DiscordApiBuilder()
                .setToken("MTAxMzg1ODkzNjgwMzI5NTMzMw.G6AZQ_.Jq3ZYCPpAv4c9zJfGm_6g0PiNTKt9d4ZNeFy6w")
                .addIntents(Intent.GUILD_MEMBERS, Intent.MESSAGE_CONTENT)
                .login()
                .join();

        discordApi.updateActivity("Kithard.pl -> 21.10.2022");

        SlashCommand.with("nagroda", "Odbierz nagrode!")
                .addOption(SlashCommandOption.create(SlashCommandOptionType.STRING, "nickname", "Nick z gry", true))
                .createGlobal(discordApi)
                .join();

        SlashCommand.with("xd", "xd")
                .createGlobal(discordApi)
                .join();

        discordApi.addServerMemberJoinListener(event -> {
            Optional<TextChannel> optionalTextChannel = event.getServer().getApi().getTextChannelById(1013861113605140530L);
            if (!optionalTextChannel.isPresent()) {
                return;
            }

            TextChannel textChannel = optionalTextChannel.get();
            User user = event.getUser();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Powitajmy nowego użyktownika na naszym discordzie!")
                    .setDescription(
                            "Witaj <@" + user.getId() + "> na oficjalnym discordzie serwera KitHard.pl \n" +
                                    " Jesteś **" + event.getServer().getMemberCount() + "** osobą na naszym serwerze discord! \n" +
                                    " Mamy nadzieję, że zostaniesz z nami na dłużej!"
                    )
                    .setColor(Color.decode("#ffffff"))
                    .setFooter("Kithard.pl -> Powitalnia | " + TimeUtil.formatTimeMillisToDate(System.currentTimeMillis()))
                    .setImage("https://i.imgur.com/2BdqKei.jpg%22");

            textChannel.sendMessage(embedBuilder);

        });

        discordApi.addMessageCreateListener(event -> {

            if (event.getChannel().getId() == 1031965692536291458L) {
                if (event.getMessage().getAuthor().isUser()) {
                    event.getMessage().delete();
                }
            }

        });

        discordApi.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("xd")) {
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Odbierz nagrode!")
                        .setDescription("Aby odebrać nagrode: /nagroda (nick z minecrafta)")
                        .setFooter("Kithard.pl -> Nagroda | " + TimeUtil.formatTimeMillisToDate(System.currentTimeMillis()))
                        .setColor(Color.decode("#00ff00"));

                event.getSlashCommandInteraction().getChannel().get().sendMessage(embedBuilder);


            }

            if (slashCommandInteraction.getCommandName().equalsIgnoreCase("nagroda")) {
                if (event.getSlashCommandInteraction().getChannel().get().getId() != 1031965692536291458L) {
                    return;
                }

                Optional<SlashCommandInteractionOption> option = slashCommandInteraction.getOptionByName("nickname");
                if (!option.isPresent()) {
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Musisz podac swoj nick!")
                            .setFlags(MessageFlag.EPHEMERAL)
                            .respond();
                    return;
                }

                String nickname = option.get().getStringValue().get();
                if (this.rewardRepository.isClaimedByDiscordAccount(slashCommandInteraction.getUser().getId())) {
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Odebrales juz nagrode na tym koncie discord!")
                            .setFlags(MessageFlag.EPHEMERAL)
                            .respond();
                    return;
                }

                if (this.rewardRepository.isClaimedByMinecraftAccount(nickname)) {
                    slashCommandInteraction.createImmediateResponder()
                            .setContent("Odebrales juz nagrode na tym koncie minecraft!")
                            .setFlags(MessageFlag.EPHEMERAL)
                            .respond();
                    return;
                }

                this.rewardRepository.insertClaimedDiscordAccount(slashCommandInteraction.getUser().getId());
                this.rewardRepository.insertClaimedMinecraftAccount(nickname);
                slashCommandInteraction.createImmediateResponder()
                        .setContent("Pomyslnie odebrano nagrode na nick: " + nickname + "!")
                        .setFlags(MessageFlag.EPHEMERAL)
                        .respond();

            }

        });

        discordApi.addButtonClickListener(event -> {

            if (!Objects.equals(event.getButtonInteraction().getCustomId(), "verify")) {
                return;
            }

            Optional<Role> verified = event.getApi().getRoleById(977889066190241833L);
            if (!verified.isPresent()) {
                return;
            }

            if (!event.getInteraction().getUser().getRoles(event.getInteraction().getServer().get()).contains(verified.get())) {
                event.getButtonInteraction().createImmediateResponder()
                        .setContent("Pomyślnie zweryfikowano!")
                        .setFlags(MessageFlag.EPHEMERAL)
                        .respond();
                event.getInteraction().getUser().addRole(verified.get());
                return;
            }

            event.getButtonInteraction().createImmediateResponder()
                    .setContent("Jesteś już zweryfikowany!")
                    .setFlags(MessageFlag.EPHEMERAL)
                    .respond();

        });

    }

    public DatabaseService getDatabaseService() {
        return databaseService;
    }
}
