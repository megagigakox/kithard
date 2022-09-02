package pl.kithard.core.guild.command;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.util.TextUtil;

@FunnyComponent
public class GuildEnlargeCommand {

    private final CorePlugin plugin;

    public GuildEnlargeCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @FunnyCommand(
            name = "g powieksz",
            acceptsExceeded = true,
            playerOnly = true
    )
    public void handle(Player player, Guild guild) {

        if (this.plugin.getGuildCache().isNotAllowed(player, GuildPermission.GUILD_ENLARGING)) {
            return;
        }

        if (guild.getRegion().getSize() >= 45) {
            TextUtil.message(player, "&8[&4&l!&8] &cGildia posiada juz &4maksymalny &crozmiar!");
            return;
        }

        ItemStack item = new ItemStack(Material.AIR);
        if (guild.getRegion().getSize() == 25) {
            item = new ItemStack(Material.EMERALD_BLOCK, 128);
        }
        else if (guild.getRegion().getSize() == 30) {
            item = new ItemStack(Material.EMERALD_BLOCK, 192);
        }
        else if (guild.getRegion().getSize() == 35) {
            item = new ItemStack(Material.EMERALD_BLOCK, 256);
        }
        else if (guild.getRegion().getSize() == 40) {
            item = new ItemStack(Material.EMERALD_BLOCK, 512);
        }

        if (player.getInventory().containsAtLeast(item, item.getAmount())) {
            player.getInventory().removeItem(item);
            guild.getRegion().setSize(guild.getRegion().getSize() + 5);

            guild.addLog(new GuildLog(
                    GuildLogType.GUILD_ENLARGE,
                    "&f" + player.getName() + " &7powiekszyl teren gildii.")
            );

            guild.setNeedSave(true);

            int size = guild.getRegion().getSize() * 2;
            TextUtil.message(player,"&8[&3&l!&8] &7Powiekszyles rozmiar gildii do &f" + size + "&7x&f" + size);
            return;
        }

        TextUtil.message(player, "&8[&4&l!&8] &cMusisz posiadac &4" + item.getAmount() + " &cblokow emeraldow aby powiekszyc gildie!!");
    }
}
