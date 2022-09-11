package pl.kithard.discord.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import pl.kithard.core.api.database.RedisService;
import pl.kithard.core.api.database.config.DatabaseConfig;
import pl.kithard.discord.bot.command.CommandCache;
import pl.kithard.discord.bot.command.CommandListener;
import pl.kithard.discord.bot.command.impl.ClearCommand;
import pl.kithard.discord.bot.command.impl.EmbedCommand;
import pl.kithard.discord.bot.command.impl.VerifyCommand;
import pl.kithard.discord.bot.listener.ButtonClickListener;
import pl.kithard.discord.bot.listener.MemberJoinListener;
import pl.kithard.discord.bot.command.impl.RewardCommand;
import pl.kithard.discord.bot.listener.RewardListener;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private static RedisService redisService;

    public static void main(String[] args) {
        CommandCache commandCache = new CommandCache();
        commandCache.add(new VerifyCommand());
        commandCache.add(new EmbedCommand());
        commandCache.add(new ClearCommand());
        commandCache.add(new RewardCommand());
        redisService = new RedisService(DatabaseConfig.REDIS_URI);

        try {

            JDABuilder.createDefault(
                    "MTAxMzg1ODkzNjgwMzI5NTMzMw.G6AZQ_.Jq3ZYCPpAv4c9zJfGm_6g0PiNTKt9d4ZNeFy6w")
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new CommandListener(commandCache))
                    .addEventListeners(new ButtonClickListener())
                    .addEventListeners(new MemberJoinListener())
                    .addEventListeners(new RewardListener())
                    .setActivity(Activity.playing("KitHard.PL -> Serwer EasyHC 1.8.8"))
                    .build();

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static RedisService getRedisService() {
        return redisService;
    }
}
