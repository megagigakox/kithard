package pl.kithard.discord.bot.command;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CommandCache {

    private final Map<String, Command> commandMap = new ConcurrentHashMap<>();

    public void add(Command command) {
        CommandInfo commandInfo = command.getClass().getAnnotation(CommandInfo.class);
        if (commandInfo == null) {
            System.out.println("commandinfo nie moze byc nullem");
            return;
        }

        this.commandMap.put(commandInfo.name(), command);
    }

    public Optional<Command> findByName(String name) {
        return Optional.ofNullable(this.commandMap.get(name));
    }

}
