package pl.kithard.core.guild.regen;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import pl.kithard.core.CorePlugin;
import pl.kithard.core.util.LocationSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuildRegenBlockSaveTask extends BukkitRunnable {

    private final CorePlugin plugin;

    public GuildRegenBlockSaveTask(CorePlugin plugin) {
        this.plugin = plugin;
        this.runTaskTimerAsynchronously(this.plugin, 0L, 20 * 1800L);
    }

    @Override
    public void run() {
        try (
                Connection connection = this.plugin.getDatabaseService().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_guild_regen_blocks (guild, location, material, data) VALUES(?, ?, ?, ?)")
        ) {

            List<GuildRegenBlock> regenBlocks = new ArrayList<>(this.plugin.getRegenCache().getToSave());
            for (GuildRegenBlock guildRegenBlock : regenBlocks) {

                preparedStatement.setString(1, guildRegenBlock.getGuild());
                preparedStatement.setString(2, LocationSerializer.serialize(guildRegenBlock.getLocation()));
                preparedStatement.setString(3, guildRegenBlock.getMaterial().toString());
                preparedStatement.setInt(4, guildRegenBlock.getData());
                preparedStatement.addBatch();

                this.plugin.getRegenCache().getToSave().remove(guildRegenBlock);
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
