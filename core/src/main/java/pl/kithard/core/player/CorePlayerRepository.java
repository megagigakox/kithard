package pl.kithard.core.player;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.player.enderchest.PlayerEnderChest;
import pl.kithard.core.player.home.PlayerHome;
import pl.kithard.core.util.CollectionSerializer;
import pl.kithard.core.util.ItemStackSerializer;
import pl.kithard.core.util.LocationSerializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CorePlayerRepository implements DatabaseRepository<CorePlayer> {

    private final static String PREPARE_TABLE =
            "CREATE TABLE IF NOT EXISTS kithard_core_players (" +
                    "uuid CHAR(36) PRIMARY KEY NOT NULL, " +
                    "name VARCHAR(16) NOT NULL, " +
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
                    "rank_reset_cooldown BIGINT, " +
                    "kit_cooldowns TEXT, " +
                    "ignored_players TEXT, " +
                    "disabled_sell_items TEXT, " +
                    "disabled_settings TEXT, " +
                    "disabled_drop_items TEXT, " +
                    "guild_history TEXT, " +
                    "claimed_achievements TEXT, " +
                    "homes TEXT, " +
                    "mined_drops TEXT, " +
                    "deposit_items TEXT, " +
                    "achievement_progress TEXT, " +
                    "ender_chest MEDIUMTEXT)";

    private final static String INSERT =
            "INSERT INTO kithard_core_players (" +
                    "uuid, name, money, earned_money, spend_money, points, kills, deaths, assists, kill_streak, " +
                    "turbo_drop, spend_time, protection, vanish, rank_reset_cooldown, kit_cooldowns, " +
                    "ignored_players, disabled_sell_items, disabled_settings, disabled_drop_items, guild_history, claimed_achievements, " +
                    "homes, mined_drops, deposit_items, achievement_progress, ender_chest) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String UPDATE =
            "UPDATE kithard_core_players SET" +
                    "`name` = ?, `money` = ?, `earned_money` = ?, `spend_money` = ?, `points` = ?, `kills` = ?, `deaths` = ?, `assists` = ?, `kill_streak` = ?, " +
                    "`turbo_drop` = ?, `spend_time` = ?, `protection` = ?, `vanish` = ?, `rank_reset_cooldown` = ?, `kit_cooldowns` = ?, " +
                    "`ignored_players` = ?, `disabled_sell_items` = ?, `disabled_settings` = ?, `disabled_drop_items` = ?, `guild_history` = ?, `claimed_achievements` = ?, " +
                    "`homes` = ?,  `mined_drops` = ?, `deposit_items` = ?, `achievement_progress` = ?, `ender_chest` = ? WHERE `uuid` = ?";

    private final DatabaseService databaseService;

    public CorePlayerRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(PREPARE_TABLE)
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insert(CorePlayer data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)
        ) {

            preparedStatement.setString(1, data.getUuid().toString());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setDouble(3, data.getMoney());
            preparedStatement.setDouble(4, data.getEarnedMoney());
            preparedStatement.setDouble(5, data.getSpendMoney());
            preparedStatement.setInt(6, data.getPoints());
            preparedStatement.setInt(7, data.getKills());
            preparedStatement.setInt(8, data.getDeaths());
            preparedStatement.setInt(9, data.getAssists());
            preparedStatement.setInt(10, data.getKillStreak());
            preparedStatement.setDouble(11, data.getTurboDrop());
            preparedStatement.setDouble(12, data.getSpendTime());
            preparedStatement.setDouble(13, data.getProtection());
            preparedStatement.setBoolean(14, data.isVanish());
            preparedStatement.setLong(15, data.getCooldown().getRankResetCooldown());
            preparedStatement.setString(16, null);
            preparedStatement.setString(17, null);
            preparedStatement.setString(18, null);
            preparedStatement.setString(19, null);
            preparedStatement.setString(20, null);
            preparedStatement.setString(21, null);
            preparedStatement.setString(22, null);
            preparedStatement.setString(23, null);
            preparedStatement.setString(24, null);
            preparedStatement.setString(25, null);
            preparedStatement.setString(26, null);
            preparedStatement.setString(27, ItemStackSerializer.itemStackArrayToBase64(data.getEnderChest().getContents()));

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(CorePlayer data) {
        throw new NotImplementedException();
    }

    @Override
    public Collection<CorePlayer> loadAll() {
        Set<CorePlayer> corePlayers = new HashSet<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `kithard_core_players`");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {

            while (resultSet.next()) {

//                Set<UUID> ignoredPlayers = new HashSet<>();
//                if (!StringUtils.isEmpty(resultSet.getString("ignored_players"))) {
//                    for (String stringUUID : CollectionSerializer.deserializeCollection("ignored_players")) {
//                        if (stringUUID == null || stringUUID.isEmpty()) {
//                            continue;
//                        }
//
//                        ignoredPlayers.add(UUID.fromString(stringUUID));
//                    }
//                }

                CorePlayer corePlayer = new CorePlayer(
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name"),
                        resultSet.getDouble("money"),
                        resultSet.getDouble("earned_money"),
                        resultSet.getDouble("spend_money"),
                        resultSet.getInt("points"),
                        resultSet.getInt("kills"),
                        resultSet.getInt("deaths"),
                        resultSet.getInt("assists"),
                        resultSet.getInt("kill_streak"),
                        resultSet.getLong("turbo_drop"),
                        resultSet.getLong("spend_time"),
                        resultSet.getLong("protection"),
                        resultSet.getBoolean("vanish"),
                        CollectionSerializer.deserializeCollection(resultSet.getString("disabled_sell_items")),
                        CollectionSerializer.deserializeCollection(resultSet.getString("disabled_settings")),
                        CollectionSerializer.deserializeCollection(resultSet.getString("disabled_drop_items")),
                        CollectionSerializer.deserializeCollection(resultSet.getString("guild_history")),
                        CollectionSerializer.deserializeCollection(resultSet.getString("claimed_achievements")),
                        CollectionSerializer.deserializeMap(resultSet.getString("mined_drops")),
                        CollectionSerializer.deserializeMap(resultSet.getString("deposit_items")),
                        CollectionSerializer.deserializeMapLong(resultSet.getString("achievement_progress")),
                        new PlayerEnderChest(ItemStackSerializer.itemStackArrayFromBase64(resultSet.getString("ender_chest")))
                );

                String homes = resultSet.getString("homes");
                for (Map.Entry<Integer, String> entry : CollectionSerializer.deserializeMapInteger(homes).entrySet()) {
                    int id = entry.getKey();
                    String locationString = entry.getValue();
                    if (StringUtils.isEmpty(locationString)) {
                        continue;
                    }

                    Location location = LocationSerializer.deserialize(locationString);
                    corePlayer.setHome(id, location);
                }

                corePlayer.getCooldown().getKitCooldowns().putAll(CollectionSerializer.deserializeMapLong(resultSet.getString("kit_cooldowns")));
                corePlayer.getCooldown().setRankResetCooldown(resultSet.getLong("rank_reset_cooldown"));
                corePlayers.add(corePlayer);
            }
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return corePlayers;
    }

    @Override
    public void updateAll(Collection<CorePlayer> toUpdate) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)
        ) {

            for (CorePlayer corePlayer : toUpdate) {
                preparedStatement.setString(1, corePlayer.getName());
                preparedStatement.setDouble(2, corePlayer.getMoney());
                preparedStatement.setDouble(3, corePlayer.getEarnedMoney());
                preparedStatement.setDouble(4, corePlayer.getSpendMoney());
                preparedStatement.setInt(5, corePlayer.getPoints());
                preparedStatement.setInt(6, corePlayer.getKills());
                preparedStatement.setInt(7, corePlayer.getDeaths());
                preparedStatement.setInt(8, corePlayer.getAssists());
                preparedStatement.setInt(9, corePlayer.getKillStreak());
                preparedStatement.setDouble(10, corePlayer.getTurboDrop());
                preparedStatement.setDouble(11, corePlayer.getSpendTime());
                preparedStatement.setDouble(12, corePlayer.getProtection());
                preparedStatement.setBoolean(13, corePlayer.isVanish());
                preparedStatement.setLong(14, corePlayer.getCooldown().getRankResetCooldown());
                preparedStatement.setString(15, CollectionSerializer.serializeMapLong(corePlayer.getCooldown().getKitCooldowns()));

                preparedStatement.setString(16, CollectionSerializer.serializeCollection(corePlayer.getIgnoredPlayers()
                        .stream()
                        .map(UUID::toString)
                        .collect(Collectors.toSet())));

                preparedStatement.setString(17, CollectionSerializer.serializeCollection(corePlayer.getDisabledSellItems()));
                preparedStatement.setString(18, CollectionSerializer.serializeCollection(corePlayer.getDisabledSettings()));
                preparedStatement.setString(19, CollectionSerializer.serializeCollection(corePlayer.getDisabledDropItems()));
                preparedStatement.setString(20, CollectionSerializer.serializeCollection(corePlayer.getGuildHistory()));
                preparedStatement.setString(21, CollectionSerializer.serializeCollection(corePlayer.getClaimedAchievements()));

                Map<Integer, String> homeIdLocationMap = new HashMap<>();
                for (PlayerHome playerHome : corePlayer.getHomes()) {
                    if (playerHome.getLocation() == null) {
                        continue;
                    }

                    homeIdLocationMap.put(playerHome.getId(), LocationSerializer.serialize(playerHome.getLocation().clone()));
                }

                preparedStatement.setString(22, CollectionSerializer.serializeMapInteger(homeIdLocationMap));
                preparedStatement.setString(23, CollectionSerializer.serializeMap(corePlayer.getMinedDrops()));
                preparedStatement.setString(24, CollectionSerializer.serializeMap(corePlayer.getDepositItems()));
                preparedStatement.setString(25, CollectionSerializer.serializeMapLong(corePlayer.getAchievementProgress()));
                preparedStatement.setString(26, ItemStackSerializer.itemStackArrayToBase64(corePlayer.getEnderChest().getContents()));
                preparedStatement.setString(27, corePlayer.getUuid().toString());

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
