package pl.kithard.proxy.auth;

import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.api.util.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthPlayerRepository implements DatabaseRepository<AuthPlayer> {

    private final DatabaseService databaseService;

    public AuthPlayerRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_auth_players (" +
                        "name VARCHAR(16) NOT NULL, " +
                        "password TEXT, " +
                        "ip TEXT, " +
                        "first_join_time BIGINT NOT NULL, " +
                        "premium BOOLEAN NOT NULL, " +
                        "registered BOOLEAN NOT NULL)")
        ) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(AuthPlayer data) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO kithard_auth_players (name, password, ip, first_join_time, premium, registered) " +
                                "VALUES(?, ?, ?, ?, ?, ?)")
        ) {

            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, data.getIp());
            preparedStatement.setLong(4, data.getFirstJoinTime());
            preparedStatement.setBoolean(5, data.isPremium());
            preparedStatement.setBoolean(6, data.isRegistered());
            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(AuthPlayer data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_auth_players WHERE name = ?")
        ) {

            preparedStatement.setString(1, data.getName());
            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<AuthPlayer> loadAll() {
        Set<AuthPlayer> authPlayerSet = new HashSet<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_auth_players");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {

                AuthPlayer authPlayer = new AuthPlayer(
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("ip"),
                        resultSet.getLong("first_join_time"),
                        resultSet.getBoolean("premium"),
                        resultSet.getBoolean("registered")
                );

                authPlayerSet.add(authPlayer);

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return authPlayerSet;
    }

    @Override
    public void updateAll(Collection<AuthPlayer> toUpdate) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kithard_auth_players SET" +
                        "`password` = ?, `ip` = ?, `premium` = ?, `registered` = ? WHERE `name` = ?")
        ) {

            for (AuthPlayer authPlayer : toUpdate) {
                preparedStatement.setString(1, authPlayer.getPassword());
                preparedStatement.setString(2, authPlayer.getIp());
                preparedStatement.setBoolean(3, authPlayer.isPremium());
                preparedStatement.setBoolean(4, authPlayer.isRegistered());
                preparedStatement.setString(5, authPlayer.getName());
                preparedStatement.addBatch();

                authPlayer.setNeedSave(false);
            }

            preparedStatement.executeBatch();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
