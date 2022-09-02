package pl.kithard.core.recipe.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class CobbleXCommand {

    @FunnyCommand(
            name = "cobblex",
            aliases = "cx",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {

        if (InventoryUtil.hasItem(player, Material.COBBLESTONE, 576)){
            while (player.getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 576)){
                InventoryUtil.removeItem(player, Material.COBBLESTONE, 576);
                InventoryUtil.addItem(player, CustomRecipe.COBBLEX.getItem());
                TextUtil.message(player, "&8[&2&l!&8] &aPomyslnie stworzono cobblex!");
            }
        } else {

            TextUtil.message(player, "&8[&4&l!&8] &cNie posiadasz &49x64 cobblestone&c!");

        }

    }

}
