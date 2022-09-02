package pl.kithard.core.recipe.command;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.recipe.CustomRecipe;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.NumberUtil;
import pl.kithard.core.util.TextUtil;
import pl.kithard.core.util.TitleUtil;

@FunnyComponent
public class MagicChestGiveCommand {

    private final CorePlugin plugin;

    public MagicChestGiveCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "chestgive",
            acceptsExceeded = true,
            permission = "kithard.commands.chestgive"
    )
    public void handle(CommandSender sender, String[] args) {

        if (args.length < 2) {
            TextUtil.correctUsage(sender, "/chestgive (gracz/all) (ilosc)");
            return;
        }

        if (!NumberUtil.isInteger(args[1])) {
            return;
        }

        int amount = Integer.parseInt(args[1]);

        if (args[0].equalsIgnoreCase("all")) {

            for (Player it : Bukkit.getOnlinePlayers()) {
                InventoryUtil.addItem(it, ItemBuilder.from(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(amount).build());
                TitleUtil.title(it, "", "&7Caly serwer otrzymal &f" + amount + "x &bMagicznych Skrzynek&7!", 10, 50, 10);
            }

            return;
        }

        Player player = this.plugin.getServer().getPlayer(args[0]);
        if (player == null) {
            return;
        }

        InventoryUtil.addItem(player, ItemBuilder.from(CustomRecipe.MAGIC_CHEST.getItem().clone()).amount(amount).build());

    }

}
