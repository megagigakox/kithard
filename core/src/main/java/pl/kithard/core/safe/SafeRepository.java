package pl.kithard.core.safe;

import org.bukkit.inventory.ItemStack;
import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.util.ItemStackSerializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SafeRepository implements DatabaseRepository<Safe> {

    private final DatabaseService databaseService;

    public SafeRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_safes (" +
                        "id BIGINT PRIMARY KEY NOT NULL, " +
                        "owner_uuid CHAR(36) NOT NULL, " +
                        "owner_name VARCHAR(16) NOT NULL, " +
                        "first_owner_name VARCHAR(16) NOT NULL, " +
                        "create_time BIGINT NOT NULL," +
                        "contents MEDIUMTEXT)")
        ) {

            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insert(Safe data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_safes (" +
                        "id, " +
                        "owner_uuid, " +
                        "owner_name, " +
                        "first_owner_name, " +
                        "create_time, " +
                        "contents) " +
                        "VALUES(?, ?, ?, ?, ?, ?)")
        ) {

            preparedStatement.setLong(1, data.getId());
            preparedStatement.setString(2, data.getOwnerUUID().toString());
            preparedStatement.setString(3, data.getOwnerName());
            preparedStatement.setString(4, data.getFirstOwnerName());
            preparedStatement.setLong(5, data.getCreateTime());
            preparedStatement.setString(6, ItemStackSerializer.itemStackArrayToBase64(data.getContents()));
            preparedStatement.execute();

        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Safe data) {

    }

    @Override
    public Collection<Safe> loadAll() {
        Set<Safe> safes = new HashSet<>();

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_safes");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                Safe safe = new Safe(
                        resultSet.getLong("id"),
                        UUID.fromString(resultSet.getString("owner_uuid")),
                        resultSet.getString("owner_name"),
                        resultSet.getString("first_owner_name"),
                        resultSet.getLong("create_time"),
                        ItemStackSerializer.itemStackArrayFromBase64(resultSet.getString("contents"))
                );
                safes.add(safe);

            }

        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return safes;
    }

    @Override
    public void updateAll(Collection<Safe> toUpdate) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kithard_safes SET " +
                        "`owner_uuid` = ?, `owner_name` = ?, `contents` = ? WHERE `id` = ?")
        ) {

            for (Safe safe : toUpdate) {
                preparedStatement.setString(1, safe.getOwnerUUID().toString());
                preparedStatement.setString(2, safe.getOwnerName());
                preparedStatement.setString(3, ItemStackSerializer.itemStackArrayToBase64(safe.getContents()));
                preparedStatement.setLong(4, safe.getId());
                preparedStatement.addBatch();

                safe.setNeedSave(false);
            }

            preparedStatement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
