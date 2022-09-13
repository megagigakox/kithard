package pl.kithard.core.player;

import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.util.CollectionSerializer;
import pl.kithard.core.util.ItemStackSerializer;
import pl.kithard.core.util.LocationSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class CorePlayerRepository implements DatabaseRepository<CorePlayer> {

    private final DatabaseService databaseService;

    public CorePlayerRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (Connection connection = this.databaseService.getConnection()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "CREATE TABLE IF NOT EXISTS kithard_core_players (" +
                                    "uuid CHAR(36) PRIMARY KEY NOT NULL, " +
                                    "name VARCHAR(16) NOT NULL, " +
                                    "ip TEXT NOT NULL, " +
                                    "money DOUBLE PRECISION, " +
                                    "earned_money DOUBLE PRECISION, " +
                                    "spend_money DOUBLE PRECISION, " +
                                    "points INT, " +
                                    "kills INT, " +
                                    "deaths INT, " +
                                    "assists INT, " +
                                    "kill_streak INT, " +
                                    "turbo_drop BIGINT, " +
                                    "spend_time BIGINT, " +
                                    "protection BIGINT, " +
                                    "vanish BOOLEAN, " +
                                    "incognito BOOLEAN, " +
                                    "rank_reset_cooldown BIGINT, " +
                                    "kit_cooldowns TEXT, " +
                                    "ignored_players TEXT, " +
                                    "disabled_sell_items TEXT, " +
                                    "disabled_drop_items TEXT, " +
                                    "guild_history TEXT, " +
                                    "claimed_achievements TEXT, " +
                                    "homes TEXT, " +
                                    "ender_chest_1 TEXT, " +
                                    "ender_chest_2 TEXT, " +
                                    "ender_chest_3 TEXT, " +
                                    "ender_chest_4 TEXT, " +
                                    "ender_chest_5 TEXT, " +
                                    "mined_drops TEXT, " +
                                    "deposit_items TEXT, " +
                                    "achievement_progress TEXT)");

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insert(CorePlayer data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO kithard_core_players (" +
                                "uuid, name, ip, money, earned_money, spend_money, points, kills, deaths, assists, kill_streak, " +
                                "turbo_drop, spend_time, protection, vanish, incognito, rank_reset_cooldown, kit_cooldowns, " +
                                "ignored_players, disabled_sell_items, disabled_drop_items, guild_history, claimed_achievements, " +
                                "homes, ender_chest_1, ender_chest_2, ender_chest_3, ender_chest_4, ender_chest_5, mined_drops, " +
                                "deposit_items, achievement_progress) " +
                                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ) {

            preparedStatement.setString(1, data.getUuid().toString());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getIp());
            preparedStatement.setDouble(4, data.getMoney());
            preparedStatement.setDouble(5, data.getEarnedMoney());
            preparedStatement.setDouble(6, data.getSpendMoney());
            preparedStatement.setInt(7, data.getPoints());
            preparedStatement.setInt(8, data.getKills());
            preparedStatement.setInt(9, data.getDeaths());
            preparedStatement.setInt(10, data.getAssists());
            preparedStatement.setInt(11, data.getKillStreak());
            preparedStatement.setDouble(12, data.getTurboDrop());
            preparedStatement.setDouble(13, data.getSpendTime());
            preparedStatement.setDouble(14, data.getProtection());
            preparedStatement.setBoolean(15, data.isVanish());
            preparedStatement.setBoolean(16, data.isIncognito());
            preparedStatement.setLong(17, data.getCooldown().getRankResetCooldown());
            preparedStatement.setString(18, null);
            preparedStatement.setString(19, null);
            preparedStatement.setString(20, null);
            preparedStatement.setString(21, null);
            preparedStatement.setString(22, null);
            preparedStatement.setString(23, null);
            preparedStatement.setString(24, null);
            preparedStatement.setString(25, null);
            preparedStatement.setString(26, null);
            preparedStatement.setString(27, null);
            preparedStatement.setString(28, null);
            preparedStatement.setString(29, null);
            preparedStatement.setString(30, null);
            preparedStatement.setString(31, null);
            preparedStatement.setString(32, null);

            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(CorePlayer data) {

    }

    @Override
    public Collection<CorePlayer> loadAll() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM kithard_core_players")
        ) {



        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateAll(Collection<CorePlayer> toUpdate) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE kithard_core_players SET" +
                                "`name` = ?, `ip` = ?, `money` = ?, `earned_money` = ?, `spend_money` = ?, `points` = ?, `kills` = ?, `deaths` = ?, `assists` = ?, `kill_streak` = ?, " +
                                "`turbo_drop` = ?, `spend_time` = ?, `protection` = ?, `vanish` = ?, `incognito` = ?, `rank_reset_cooldown` = ?, `kit_cooldowns` = ?, " +
                                "`ignored_players` = ?, `disabled_sell_items` = ?, `disabled_drop_items` = ?, `guild_history` = ?, `claimed_achievements` = ?, " +
                                "`homes` = ?, `ender_chest_1` = ?, `ender_chest_2` = ?, `ender_chest_3` = ?, `ender_chest_4` = ?, `ender_chest_5` = ?, `mined_drops` = ?, " +
                                "`deposit_items` = ?, `achievement_progress` = ? WHERE `uuid` = ?")
        ) {

            for (CorePlayer corePlayer : toUpdate) {
                preparedStatement.setString(1, corePlayer.getName());
                preparedStatement.setString(2, corePlayer.getIp());
                preparedStatement.setDouble(3, corePlayer.getMoney());
                preparedStatement.setDouble(4, corePlayer.getEarnedMoney());
                preparedStatement.setDouble(5, corePlayer.getSpendMoney());
                preparedStatement.setInt(6, corePlayer.getPoints());
                preparedStatement.setInt(7, corePlayer.getKills());
                preparedStatement.setInt(8, corePlayer.getDeaths());
                preparedStatement.setInt(9, corePlayer.getAssists());
                preparedStatement.setInt(10, corePlayer.getKillStreak());
                preparedStatement.setDouble(11, corePlayer.getTurboDrop());
                preparedStatement.setDouble(12, corePlayer.getSpendTime());
                preparedStatement.setDouble(13, corePlayer.getProtection());
                preparedStatement.setBoolean(14, corePlayer.isVanish());
                preparedStatement.setBoolean(15, corePlayer.isIncognito());
                preparedStatement.setLong(16, corePlayer.getCooldown().getRankResetCooldown());
                preparedStatement.setString(17, CollectionSerializer.serializeMapLong(corePlayer.getCooldown().getKitCooldowns()));
                preparedStatement.setString(18, CollectionSerializer.serializeCollection(corePlayer.getIgnoredPlayers()
                        .stream()
                        .map(UUID::toString)
                        .collect(Collectors.toSet())));
                preparedStatement.setString(19, CollectionSerializer.serializeCollection(corePlayer.getDisabledSellItems()));
                preparedStatement.setString(20, CollectionSerializer.serializeCollection(corePlayer.getDisabledDropItems()));
                preparedStatement.setString(21, CollectionSerializer.serializeCollection(corePlayer.getGuildHistory()));

                //Tutaj serializacja odebranych osiagniec
                preparedStatement.setString(22, "");

                Map<Integer, String> homeIdLocationMap = new HashMap<>();
                for (PlayerHome playerHome : corePlayer.getHomes()) {
                    if (playerHome.getLocation() == null) {
                        continue;
                    }

                    homeIdLocationMap.put(playerHome.getId(), LocationSerializer.serialize(playerHome.getLocation()));
                }
                preparedStatement.setString(22, CollectionSerializer.serializeMapInteger(homeIdLocationMap));
                preparedStatement.setString(23, "");
                preparedStatement.setString(24, "");
                preparedStatement.setString(25, "");
                preparedStatement.setString(26, "");
                preparedStatement.setString(27, "");
                preparedStatement.setString(28, "");
                preparedStatement.setString(29, "");
                preparedStatement.setString(30, "");
                preparedStatement.setString(31, "");
                preparedStatement.setString(32, corePlayer.getUuid().toString());


                corePlayer.setNeedSave(false);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
