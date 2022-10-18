package pl.kithard.core.guild.logblock;

import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.api.util.TimeUtil;
import pl.kithard.core.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildLogBlockGui {

    private final CorePlugin plugin;

    public GuildLogBlockGui(CorePlugin plugin) {
        this.plugin = plugin;
    }

    public void open(Player player, Block block) {
        Gui gui = Gui.gui()
                .rows(3)
                .title(TextUtil.component("&3&lLogBlock"))
                .create();

        gui.getFiller().fill(ItemStackBuilder.of(GuiHelper.BLACK_STAINED_GLASS_PANE).asGuiItem());

        gui.setItem(2, 4, ItemStackBuilder.of(Material.DIAMOND_PICKAXE)
                .name("&7Interakcja&8: &3Zniszczone bloki")
                .asGuiItem(event -> openType(player, GuildLogBlockType.BREAK, block)));
        gui.setItem(2, 6, ItemStackBuilder.of(Material.STONE)
                .name("&7Interakcja&8: &3Polozone bloki")
                .asGuiItem(event -> openType(player, GuildLogBlockType.PLACE, block)));

        gui.setDefaultClickAction(event -> event.setCancelled(true));
        gui.open(player);
    }

    public void openType(Player player, GuildLogBlockType logBlockType, Block block) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {

           Gui gui = Gui.gui()
                   .rows(6)
                   .title(TextUtil.component("&3&lLogBlock &8(" + logBlockType + ")"))
                   .create();

           GuiHelper.fillColorGui6(gui);
           gui.setItem(6, 5, ItemStackBuilder.of(GuiHelper.BACK_ITEM).asGuiItem(event -> open(player, block)));

           try(
                   Connection connection = this.plugin.getDatabaseService().getConnection();
                   PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guild_logblocks WHERE `location` = ? AND `type` = ? ORDER BY `date` DESC LIMIT 28");
           ) {

               preparedStatement.setString(1, LocationSerializer.serialize(block.getLocation()));
               preparedStatement.setString(2, logBlockType.toString());
               ResultSet resultSet = preparedStatement.executeQuery();
               while (resultSet.next()) {

                   GuildLogBlock guildLogBlock = new GuildLogBlock(
                           GuildLogBlockType.valueOf(resultSet.getString("type")),
                           resultSet.getString("player"),
                           Material.valueOf(resultSet.getString("material")),
                           (byte) resultSet.getInt("data"),
                           LocationSerializer.deserialize(resultSet.getString("location")),
                           resultSet.getLong("date")
                   );

                   gui.addItem(ItemStackBuilder.of(new ItemStack(guildLogBlock.getMaterial(), 1, guildLogBlock.getData()))
                           .lore(
                                   "",
                                   " &7Typ interakcji&8: &f" + guildLogBlock.getType(),
                                   " &7Interakcje z tym blokiem wykonal&8: &f" + guildLogBlock.getPlayer(),
                                   " &7Data wykonania interakcji&8: &f" + TimeUtil.formatTimeMillisToDate(guildLogBlock.getDate()),
                                   ""
                           )
                           .asGuiItem());

               }

               resultSet.close();

           } catch (SQLException e) {
               e.printStackTrace();
           }

            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
                gui.setDefaultClickAction(event -> event.setCancelled(true));
                gui.open(player);
            });


        });

    }
}
