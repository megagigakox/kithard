package pl.kithard.core.player.backup;

import org.apache.commons.lang.NotImplementedException;
import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.util.CollectionSerializer;
import pl.kithard.core.util.ItemStackSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class PlayerBackupRepository implements DatabaseRepository<PlayerBackup> {

    private final DatabaseService databaseService;

    public PlayerBackupRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_backups(" +
                        "id CHAR(36) NOT NULL, " +
                        "player_name VARCHAR(16) NOT NULL, " +
                        "type TEXT NOT NULL, " +
                        "create_time BIGINT NOT NULL, " +
                        "killer TEXT NOT NULL, " +
                        "inventory TEXT NOT NULL, " +
                        "armor TEXT NOT NULL, " +
                        "ping INT NOT NULL, " +
                        "lost_points INT NOT NULL, " +
                        "tps FLOAT NOT NULL, " +
                        "admin_restored TEXT)")
        ) {

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(PlayerBackup data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_backups(" +
                        "id, player_name, type, create_time, killer, inventory, armor, ping, lost_points, tps, admin_restored) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ) {

            preparedStatement.setString(1, data.getId().toString());
            preparedStatement.setString(2, data.getPlayerName());
            preparedStatement.setString(3, data.getType().toString());
            preparedStatement.setLong(4, data.getCreateTime());
            preparedStatement.setString(5, data.getKiller());
            preparedStatement.setString(6, ItemStackSerializer.itemStackArrayToBase64(data.getInventory()));
            preparedStatement.setString(7, ItemStackSerializer.itemStackArrayToBase64(data.getArmor()));
            preparedStatement.setInt(8, data.getPing());
            preparedStatement.setInt(9, data.getLostPoints());
            preparedStatement.setFloat(10, data.getTps());
            preparedStatement.setString(11, null);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(PlayerBackup data) {
        throw new NotImplementedException();
    }

    @Override
    public Collection<PlayerBackup> loadAll() {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(Collection<PlayerBackup> toUpdate) {
        throw new NotImplementedException();
    }

    public void update(PlayerBackup playerBackup) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kithard_backups SET" +
                        "`admin_restored` = ? WHERE `id` = ?")
        ) {

            preparedStatement.setString(1, CollectionSerializer.serializeMapLongString(playerBackup.getAdminsRestored()));
            preparedStatement.setString(2, playerBackup.getId().toString());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
