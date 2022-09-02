package pl.kithard.core.recipe.command;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class AdminItemsCommand {

    @FunnyCommand(
            name = "aitems",
            permission = "kithard.commands.aitems",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player) {

        Gui gui = Gui.gui()
                .title(TextUtil.component("&3&lAdmin Items"))
                .rows(5)
                .create();

        GuiHelper.fillColorGui5(gui);

        for (CustomRecipe customRecipe : CustomRecipe.values()) {

            gui.addItem(ItemBuilder.from(customRecipe.getItem())
                    .asGuiItem(inventoryClickEvent -> {

                        InventoryUtil.addItem(player, customRecipe.getItem());

                    }));

        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

}
