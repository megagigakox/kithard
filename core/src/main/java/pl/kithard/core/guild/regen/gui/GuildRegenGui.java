package pl.kithard.core.guild.regen.gui;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.guild.Guild;
import pl.kithard.core.guild.panel.gui.GuildPanelGui;
import pl.kithard.core.guild.regen.GuildRegenBlock;
import pl.kithard.core.guild.regen.GuildRegenTask;
import pl.kithard.core.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class GuildRegenGui {

    private final CorePlugin plugin;

    public GuildRegenGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, Guild guild) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {

            Gui gui = Gui.gui()
                    .rows(5)
                    .title(TextUtil.component("&3&lZregeneruj cuboid"))
                    .create();

            GuiHelper.fillColorGui5(gui);
            gui.setItem(5, 5, ItemStackBuilder.of(GuiHelper.BACK_ITEM)
                    .asGuiItem(event -> new GuildPanelGui(plugin).openPanel(player, guild)));

            LinkedList<GuildRegenBlock> regenBlocks = this.plugin.getGuildRepository().loadRegenBlocks(guild.getTag());
            int cost = (int) (regenBlocks.size() / 7.7);
            ItemStackBuilder itemStackBuilder = ItemStackBuilder.of(Material.LEVER)
                    .name("&3&lRegeneracja");

            itemStackBuilder.appendLore(
                    "",
                    " &8(&4&l!&8) &cPamietaj ze regeneracja dziala tylko",
                    " &cw godzinach wolnych od &4TNT&c! Czyli &422:00 - 18:00&c.",
                    "",
                    " &7Koszt regeneracji&8: &f" + cost + " blokow emeraldow.",
                    " &7Blokow do zregenerowania&8: &f" + regenBlocks.size(),
                    "",
                    "&7Kliknij &fprawym &7aby rozpoczac regeneracje cuboida!"
            );


            gui.setItem(3, 5, itemStackBuilder.asGuiItem(event -> {

                if (regenBlocks.isEmpty()) {
                    TextUtil.message(player, "&8(&4&l!&8) &cNie ma zadnych blokow do regeneracji!");
                    return;
                }

                if (!player.getInventory().containsAtLeast(new ItemStack(Material.EMERALD_BLOCK), cost)) {
                    TextUtil.message(
                            player,
                            "&8(&4&l!&8) &cNie posiadasz wystarczajacej ilosci blokow emeraldow! &4(" + cost + " blokow)"
                    );
                    return;
                }

                if (this.plugin.getRegenCache().getCurrentlyRegeneratingGuilds().contains(guild.getTag())) {
                    TextUtil.message(player, "&8(&4&l!&8) &cTwoj teren gildii juz jest podczas regeneracji!");
                    return;
                }

                player.closeInventory();
                InventoryUtil.removeItems(player.getInventory(), new ItemStack(Material.EMERALD_BLOCK), cost);
                TextUtil.message(player, "&8(&2&l!&8) &aPomyslnie rozpoczeto regeneracje terenu gildii!");
                this.plugin.getRegenCache().getCurrentlyRegeneratingGuilds().add(guild.getTag());
                new GuildRegenTask(this.plugin, new LinkedList<>(regenBlocks), player.getUniqueId(), guild);
                try (
                        Connection connection = this.plugin.getDatabaseService().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_guild_regen_blocks WHERE guild = ?")
                ) {

                    preparedStatement.setString(1, guild.getTag());
                    preparedStatement.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));

            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                gui.setDefaultClickAction(event -> event.setCancelled(true));
                gui.open(player);
            });

        });
    }
}
