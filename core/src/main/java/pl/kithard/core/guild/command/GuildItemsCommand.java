package pl.kithard.core.guild.command;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

import java.util.Arrays;
import java.util.List;

@FunnyComponent
public class GuildItemsCommand {

    @FunnyCommand(
            name = "g itemy",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player) {
        Gui gui = Gui.gui()
                .title(TextUtil.component("&9&lItemy na gildie"))
                .rows(3)
                .create();

        GuiHelper.fillColorGui3(gui);

        List<ItemStack> itemsForGuildCreate;

        if (player.hasPermission("kithard.premium.items")) {
            itemsForGuildCreate = CoreConstants.PREMIUM_GUILD_ITEMS;
        } else {
            itemsForGuildCreate = CoreConstants.PLAYER_GUILD_ITEMS;
        }

        int i = 8;
        for (ItemStack itemStack : itemsForGuildCreate) {
            i++;

            gui.setItem(i, ItemBuilder.from(itemStack)
                    .lore(TextUtil.component(Arrays.asList(
                            "",
                            " &7W ekwipunku&8: &f" + InventoryUtil.countItemsIgnoreItemMeta(player, itemStack) + "&8/&f" + itemStack.getAmount(),
                            player.hasPermission("kithard.premium.items") ? "&7Posiadasz range &epremium&7, twoje itemy na gildie są &bzmniejszone&7!" : ""
                    )))
                    .asGuiItem());

        }

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);

    }

}
