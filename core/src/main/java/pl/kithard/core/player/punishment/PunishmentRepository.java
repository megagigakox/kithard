package pl.kithard.core.player.punishment;

import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.player.punishment.type.Ban;
import pl.kithard.core.player.punishment.type.BanIP;
import pl.kithard.core.player.punishment.type.Mute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PunishmentRepository {

    private final DatabaseService databaseService;

    public PunishmentRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void prepareTables() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement a = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_punishment_bans(" +
                        "punished TEXT NOT NULL, " +
                        "admin TEXT NOT NULL, " +
                        "time BIGINT NOT NULL," +
                        "reason TEXT NOT NULL)"
                );
                PreparedStatement b = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_punishment_ipbans(" +
                        "punished_ip TEXT NOT NULL, " +
                        "admin TEXT NOT NULL, " +
                        "reason TEXT NOT NULL)"
                );
                PreparedStatement c = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kithard_punishment_mutes(" +
                        "punished TEXT NOT NULL, " +
                        "admin TEXT NOT NULL, " +
                        "time BIGINT NOT NULL," +
                        "reason TEXT NOT NULL)"
                )
        ) {

            a.execute();
            b.execute();
            c.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBan(Ban ban) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT kithard_punishment_bans(" +
                        "punished, admin, time, reason) VALUES(?, ?, ?, ?)"
                )
        ) {

            preparedStatement.setString(1, ban.getPunished());
            preparedStatement.setString(2, ban.getAdmin());
            preparedStatement.setLong(3, ban.getTime());
            preparedStatement.setString(4, ban.getReason());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertBanIp(BanIP ban) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT kithard_punishment_ipbans(" +
                        "punished_ip, admin, reason) VALUES(?, ?, ?)"
                )
        ) {

            preparedStatement.setString(1, ban.getPunishedIP());
            preparedStatement.setString(2, ban.getAdmin());
            preparedStatement.setString(3, ban.getReason());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertMute(Mute ban) {

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT kithard_punishment_mutes(" +
                        "punished, admin, time, reason) VALUES(?, ?, ?, ?)"
                )
        ) {

            preparedStatement.setString(1, ban.getPunished());
            preparedStatement.setString(2, ban.getAdmin());
            preparedStatement.setLong(3, ban.getTime());
            preparedStatement.setString(4, ban.getReason());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteBan(Ban ban) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_punishment_bans WHERE punished = ?")
        ) {

            preparedStatement.setString(1, ban.getPunished());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBanIp(BanIP ban) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_punishment_ipbans WHERE punished_ip = ?")
        ) {

            preparedStatement.setString(1, ban.getPunishedIP());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMute(Mute ban) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM kithard_punishment_mutes WHERE punished = ?")
        ) {

            preparedStatement.setString(1, ban.getPunished());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ban> loadBans() {
        List<Ban> list = new ArrayList<>();

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_punishment_bans");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                list.add(new Ban(
                        resultSet.getString("punished"),
                        resultSet.getString("admin"),
                        resultSet.getLong("time"),
                        resultSet.getString("reason")
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<BanIP> loadIPBans() {
        List<BanIP> list = new ArrayList<>();

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_punishment_ipbans");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                list.add(new BanIP(
                        resultSet.getString("punished_ip"),
                        resultSet.getString("admin"),
                        resultSet.getString("reason")
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Mute> loadMutes() {
        List<Mute> list = new ArrayList<>();

        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_punishment_mutes");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

                list.add(new Mute(
                        resultSet.getString("punished"),
                        resultSet.getString("admin"),
                        resultSet.getLong("time"),
                        resultSet.getString("reason")
                ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
