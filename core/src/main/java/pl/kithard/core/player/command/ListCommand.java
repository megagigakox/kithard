package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import pl.kithard.core.util.TextUtil;

import java.util.stream.Collectors;

@FunnyComponent
public class ListCommand {

    @FunnyCommand(
            name = "list",
            acceptsExceeded = true
    )
    public void handle(CommandSender sender) {
        TextUtil.message(sender,"&8(&3&l!&8) &7Aktuanie na serwerze przebywa &3&l" + Bukkit.getOnlinePlayers().size() + " &7graczy!");
        if (sender.hasPermission("kithard.commands.list")) {
            TextUtil.message(sender, "&8» &b" + StringUtils.join(Bukkit.getOnlinePlayers()
                    .stream()
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList()), "&8,&b "));
        }
    }

}
