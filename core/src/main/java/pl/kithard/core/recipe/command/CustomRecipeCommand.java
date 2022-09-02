package pl.kithard.core.recipe.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.recipe.gui.CustomRecipeGui;

@FunnyComponent
public class CustomRecipeCommand {

    @FunnyCommand(
            name = "craftingi",
            aliases = "craft",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        new CustomRecipeGui().openBoyFarmer(player);
    }


}
