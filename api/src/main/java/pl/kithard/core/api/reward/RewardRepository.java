package pl.kithard.core.api.reward;

import pl.kithard.core.api.database.mysql.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RewardRepository {

    private final DatabaseService databaseService;

    public RewardRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement table1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_reward_users(" +
                        "user_id BIGINT NOT NULL)");
                PreparedStatement table2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_reward_players(" +
                        "user_name TEXT NOT NULL, " +
                        "claimed BOOLEAN NOT NULL)");
        ) {

            table1.execute();
            table2.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertClaimedDiscordAccount(long userId) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_reward_users(user_id) VALUES(?)");
        ) {

            preparedStatement.setLong(1, userId);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertClaimedMinecraftAccount(String nickname) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kithard_reward_players(user_name, claimed) VALUES(?, ?)");
        ) {

            preparedStatement.setString(1, nickname);
            preparedStatement.setBoolean(2, false);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMinecraftAccount(String nickname, boolean claimed) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE kithard_reward_players SET user_name = ?, claimed = ?");
        ) {

            preparedStatement.setString(1, nickname);
            preparedStatement.setBoolean(2, claimed);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isClaimedByDiscordAccount(long userId) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_reward_users");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                if (resultSet.getLong("user_id") == userId) {
                    return true;
                }

            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> loadAllWhoNeedClaim() {

        List<String> list = new ArrayList<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_reward_players WHERE claimed = 0");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                list.add(resultSet.getString("user_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isClaimedByMinecraftAccount(String nickname) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_reward_players");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                if (resultSet.getString("user_name").equalsIgnoreCase(nickname)) {
                    return true;
                }

            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
