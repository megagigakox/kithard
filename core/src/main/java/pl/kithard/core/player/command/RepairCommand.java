package pl.kithard.core.player.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class RepairCommand {

    @FunnyCommand(
            name = "repair",
            aliases = "fix",
            permission = "kithard.commands.repair",
            playerOnly = true,
            acceptsExceeded = true
    )
    public void handle(Player player, String[] args) {
        ItemStack itemInHand = player.getItemInHand();

        if (args.length < 1) {
            if (itemInHand.getType().getMaxDurability() > 0) {
                itemInHand.setDurability((short) 0);
                TextUtil.message(player, "&aNaprawiono przedmiot.");
            } else {
                TextUtil.message(player,"&8[&4&l!&8] &cNie możesz naprawic tego przedmiotu.");
            }
            return;
        }

        if (!args[0].equalsIgnoreCase("all")) {
            TextUtil.correctUsage(player, "/repair all");
            return;
        }

        if (!player.hasPermission("kithard.commands.repair.all")) {
            TextUtil.insufficientPermission(player, "kithard.commands.repair.all");
            return;
        }

        PlayerInventory inventory = player.getInventory();
        for (ItemStack content : inventory.getContents()) {
            if (content != null
                    && content.getType() != null
                    && content.getType().getMaxDurability() > 0) {
                content.setDurability((short) 0);
            }
        }

        for (ItemStack content : inventory.getArmorContents()) {
            if (content != null && content.getType().getMaxDurability() > 0) {
                content.setDurability((short) 0);
            }
        }

        TextUtil.message(player, "&aNaprawiono przedmioty.");
    }

}
