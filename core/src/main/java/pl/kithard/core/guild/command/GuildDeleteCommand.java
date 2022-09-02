package pl.kithard.core.guild.command;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.util.GuiHelper;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildDeleteCommand {

    private final CorePlugin plugin;

    public GuildDeleteCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g usun",
            aliases = "g delete",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, Guild guild) {

        if (!guild.isOwner(player.getUniqueId())) {
            TextUtil.message(player, "&8[&4&l!&8] &cNie jestes liderem gildii!");
            return;
        }

        Gui gui = Gui.gui()
                .title(TextUtil.component("&cPotwierdz usuniecie gildii:"))
                .rows(1)
                .create();

        gui.getFiller().fill(ItemBuilder.from(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(3, ItemBuilder.from(new ItemStack(351,1, (short) 10))
                .name(TextUtil.component("&cPotwierdz usuniecie gildii"))
                .asGuiItem(inventoryClickEvent -> {

                    Bukkit.broadcastMessage(TextUtil.color("&8[&3&l!&8] &7Gildia &8[&b" + guild.getTag() + "&8] &7zostala usunieta przez &f" + player.getName() + "&7!"));
                    this.plugin.getGuildFactory().delete(guild);
                    player.closeInventory();

                }));

        gui.setItem(5, ItemBuilder.from(new ItemStack(351,1, (short) 1))
                .name(TextUtil.component("&aZrezygnuj z usuniecia gildii"))
                .asGuiItem(inventoryClickEvent -> player.closeInventory()));

        gui.setDefaultClickAction(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));
        gui.open(player);
    }

}
