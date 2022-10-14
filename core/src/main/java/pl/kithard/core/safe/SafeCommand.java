package pl.kithard.core.safe;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.ItemStackBuilder;

@FunnyComponent
public class SafeCommand {

    @FunnyCommand(
            name = "lom",
            permission = "kithard.commands.safe.lom",
            acceptsExceeded = true
    )
    public void handle(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);

        InventoryUtil.addItem(player, ItemStackBuilder.of(CustomRecipe.CROWBAR.getItem().clone()).amount(amount).asItemStack());
    }

}
