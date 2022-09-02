package pl.kithard.core.guild.command.admin;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CoreConstants;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.InventoryUtil;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildAdminGiveItemsCommand {

    private final CorePlugin plugin;

    public GuildAdminGiveItemsCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "ga itemy",
            permission = "kithard.commands.guildadmin.items",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        if (args.length < 1) {
            TextUtil.correctUsage(player, "/ga itemy (gracz)");
            return;
        }

        Player target = this.plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            return;
        }

        for (ItemStack itemStack : CoreConstants.PLAYER_GUILD_ITEMS) {

            InventoryUtil.addItem(target, itemStack);

        }

    }

}
