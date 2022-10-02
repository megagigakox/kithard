package pl.kithard.core.guild;

import org.bukkit.Material;
import pl.kithard.core.api.database.mysql.DatabaseRepository;
import pl.kithard.core.api.database.mysql.DatabaseService;
import pl.kithard.core.guild.log.GuildLog;
import pl.kithard.core.guild.log.GuildLogType;
import pl.kithard.core.guild.permission.GuildPermission;
import pl.kithard.core.guild.permission.GuildPermissionScheme;
import pl.kithard.core.guild.regen.GuildRegenBlock;
import pl.kithard.core.util.CollectionSerializer;
import pl.kithard.core.util.ItemStackSerializer;
import pl.kithard.core.util.LocationSerializer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GuildRepository implements DatabaseRepository<Guild> {

    private final static String PREPARE_TABLE_GUILDS =
            "CREATE TABLE IF NOT EXISTS kithard_guilds (" +
                    "tag VARCHAR(5) NOT NULL, " +
                    "name VARCHAR(32) NOT NULL, " +
                    "owner CHAR(36) NOT NULL, " +
                    "guild_region_center TEXT NOT NULL, " +
                    "guild_region_size INT, " +
                    "home TEXT NOT NULL," +
                    "lives INT, " +
                    "points INT, " +
                    "kills INT, " +
                    "deaths INT, " +
                    "expire_time BIGINT, " +
                    "create_time BIGINT, " +
                    "last_attack_time BIGINT, " +
                    "last_explode_time BIGINT, " +
                    "friendly_fire BOOLEAN, " +
                    "ally_fire BOOLEAN, " +
                    "deputies TEXT, " +
                    "allies TEXT, " +
                    "warehouse MEDIUMTEXT)";

    private final static String PREPARE_TABLE_MEMBERS =
            "CREATE TABLE IF NOT EXISTS kithard_guild_members (" +
                    "guild VARCHAR(5) NOT NULL, " +
                    "uuid CHAR(36) NOT NULL, " +
                    "name VARCHAR(16) NOT NULL, " +
                    "allowed_permissions TEXT)";

    private final static String PREPARE_TABLE_SCHEMES =
            "CREATE TABLE IF NOT EXISTS kithard_guild_permission_schemes (" +
                    "guild VARCHAR(5) NOT NULL, " +
                    "name VARCHAR(16) NOT NULL, " +
                    "allowed_permissions TEXT)";

    private final static String PREPARE_TABLE_REGEN_BLOCKS =
            "CREATE TABLE IF NOT EXISTS kithard_guild_regen_blocks (" +
                    "guild VARCHAR(5) NOT NULL, " +
                    "location TEXT NOT NULL, " +
                    "material TEXT NOT NULL, " +
                    "data INT NOT NULL)";

    private final static String PREPARE_TABLE_LOGS =
            "CREATE TABLE IF NOT EXISTS kithard_guild_logs (" +
                    "guild VARCHAR(5) NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "action TEXT NOT NULL, " +
                    "date LONG NOT NULL)";

    private final static String INSERT_GUILD =
            "INSERT INTO kithard_guilds (" +
                    "tag, name, owner, guild_region_center, guild_region_size, home, lives, points, kills, deaths, expire_time, " +
                    "create_time, last_attack_time, last_explode_time, friendly_fire, ally_fire, deputies, allies, warehouse) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String INSERT_MEMBER =
            "INSERT INTO kithard_guild_members (guild, uuid, name, allowed_permissions) VALUES(?, ?, ?, ?)";

    private final static String INSERT_SCHEME =
            "INSERT INTO kithard_guild_permission_schemes (guild, name, allowed_permissions) VALUES(?, ?, ?)";

    private final static String INSERT_LOG =
            "INSERT INTO kithard_guild_logs (guild, type, action, date) VALUES(?, ?, ?, ?)";

    private final static String UPDATE_GUILD =
            "UPDATE kithard_guilds SET" +
                    "`owner` = ?, `guild_region_center` = ?, `guild_region_size` = ?, `home` = ?, `lives` = ?, `points` = ?, " +
                    "`kills` = ?, `deaths` = ?, `expire_time` = ?, `create_time` = ?, " +
                    "`last_attack_time` = ?, `last_explode_time` = ?, `friendly_fire` = ?, `ally_fire` = ?, " +
                    "`deputies` = ?, `allies` = ?, `warehouse` = ? WHERE `tag` = ?";

    private final static String UPDATE_MEMBER =
            "UPDATE kithard_guild_members SET" +
                    "`name` = ?, `allowed_permissions` = ? WHERE `uuid` = ?";

    private final static String UPDATE_SCHEME =
            "UPDATE kithard_guild_permission_schemes SET" +
                    "`allowed_permissions` = ? WHERE `name` = ? AND `guild` = ?";

    private final static String DELETE_SCHEME =
            "DELETE FROM kithard_guild_permission_schemes WHERE name = ? AND guild = ?";

    private final Logger logger;
    private final DatabaseService databaseService;
    private final GuildCache guildCache;

    public GuildRepository(Logger logger, DatabaseService databaseService, GuildCache guildCache) {
        this.logger = logger;
        this.databaseService = databaseService;
        this.guildCache = guildCache;
    }

    public void insertMember(GuildMember guildMember) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MEMBER)
        ) {

            preparedStatement.setString(1, guildMember.getGuild());
            preparedStatement.setString(2, guildMember.getUuid().toString());
            preparedStatement.setString(3, guildMember.getName());
            preparedStatement.setString(4, null);

            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertScheme(GuildPermissionScheme guildPermissionScheme) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SCHEME)
        ) {

            preparedStatement.setString(1, guildPermissionScheme.getGuild());
            preparedStatement.setString(2, guildPermissionScheme.getName());
            preparedStatement.setString(3, null);

            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteScheme(GuildPermissionScheme scheme) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCHEME)
        ) {

            preparedStatement.setString(1, scheme.getName());
            preparedStatement.setString(2, scheme.getGuild());
            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertLog(GuildLog guildLog) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOG)
        ) {

            preparedStatement.setString(1, guildLog.getGuild());
            preparedStatement.setString(2, guildLog.getType().toString());
            preparedStatement.setString(3, guildLog.getAction());
            preparedStatement.setLong(4, guildLog.getDate());
            preparedStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareTable() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement guildStatement = connection.prepareStatement(PREPARE_TABLE_GUILDS);
                PreparedStatement memberStatement = connection.prepareStatement(PREPARE_TABLE_MEMBERS);
                PreparedStatement schemeStatement = connection.prepareStatement(PREPARE_TABLE_SCHEMES);
                PreparedStatement regenBlockStatement = connection.prepareStatement(PREPARE_TABLE_REGEN_BLOCKS);
                PreparedStatement logsStatement = connection.prepareStatement(PREPARE_TABLE_LOGS);
        ) {

            guildStatement.execute();
            memberStatement.execute();
            schemeStatement.execute();
            regenBlockStatement.execute();
            logsStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Guild data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GUILD)
        ) {

            preparedStatement.setString(1, data.getTag());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getOwner().toString());
            preparedStatement.setString(4, LocationSerializer.serialize(data.getRegion().getCenter().clone()));
            preparedStatement.setInt(5, data.getRegion().getSize());
            preparedStatement.setString(6, LocationSerializer.serialize(data.getHome()));
            preparedStatement.setInt(7, data.getLives());
            preparedStatement.setInt(8, data.getPoints());
            preparedStatement.setInt(9, data.getKills());
            preparedStatement.setInt(10, data.getDeaths());
            preparedStatement.setLong(11, data.getExpireTime());
            preparedStatement.setLong(12, data.getCreateTime());
            preparedStatement.setLong(13, data.getLastAttackTime());
            preparedStatement.setLong(14, data.getLastExplodeTime());
            preparedStatement.setBoolean(15, data.isFriendlyFire());
            preparedStatement.setBoolean(16, data.isAllyFire());
            preparedStatement.setString(17, null);
            preparedStatement.setString(18, null);
            preparedStatement.setString(19, ItemStackSerializer.itemStackArrayToBase64(data.getWarehouseContents()));

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Guild data) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement guildStatement = connection.prepareStatement("DELETE FROM kithard_guilds WHERE tag = ?");
                PreparedStatement memberStatement = connection.prepareStatement("DELETE FROM kithard_guild_members WHERE guild = ?");
                PreparedStatement schemeStatement = connection.prepareStatement("DELETE FROM kithard_guild_permission_schemes WHERE guild = ?");
                PreparedStatement regenStatement = connection.prepareStatement("DELETE FROM kithard_guild_regen_blocks WHERE guild = ?");
                PreparedStatement logsStatement = connection.prepareStatement("DELETE FROM kithard_guild_logs WHERE guild = ?")
        ) {

            guildStatement.setString(1, data.getTag());
            guildStatement.execute();

            memberStatement.setString(1, data.getTag());
            memberStatement.execute();

            schemeStatement.setString(1, data.getTag());
            schemeStatement.execute();

            regenStatement.setString(1, data.getTag());
            regenStatement.execute();

            logsStatement.setString(1, data.getTag());
            logsStatement.execute();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMembers() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guild_members");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {

                String guildTag = resultSet.getString("guild");
                Guild guild = this.guildCache.findByTag(guildTag);

                GuildMember guildMember = new GuildMember(
                        guildTag,
                        UUID.fromString(resultSet.getString("uuid")),
                        resultSet.getString("name")
                );

                guildMember.setAllowedPermissions(CollectionSerializer.deserializeCollection(resultSet.getString("allowed_permissions"))
                        .stream()
                        .map(GuildPermission::valueOf)
                        .collect(Collectors.toSet()));
                guild.getMembers().add(guildMember);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadSchemes() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guild_permission_schemes");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {

                Guild guild = this.guildCache.findByTag(resultSet.getString("guild"));
                GuildPermissionScheme guildPermissionScheme = new GuildPermissionScheme(
                        resultSet.getString("guild"),
                        resultSet.getString("name")
                );
                guildPermissionScheme.setAllowedPermissions(CollectionSerializer.deserializeCollection(resultSet.getString("allowed_permissions"))
                        .stream()
                        .map(GuildPermission::valueOf)
                        .collect(Collectors.toSet()));
                guild.getPermissionSchemes().add(guildPermissionScheme);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<GuildRegenBlock> loadRegenBlocks(String guild) {
        LinkedList<GuildRegenBlock> guildRegenBlocks = new LinkedList<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guild_regen_blocks WHERE guild = ?")
        ) {

            preparedStatement.setString(1, guild);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                GuildRegenBlock guildRegenBlock = new GuildRegenBlock(
                        resultSet.getString("guild"),
                        LocationSerializer.deserialize(resultSet.getString("location")),
                        Material.valueOf(resultSet.getString("material")),
                        Byte.parseByte(resultSet.getString("data"))
                );

                guildRegenBlocks.add(guildRegenBlock);
            }

            resultSet.close();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return guildRegenBlocks;
    }

    public void loadLogs() {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guild_logs");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {

                Guild guild = this.guildCache.findByTag(resultSet.getString("guild"));
                GuildLog guildLog = new GuildLog(
                        resultSet.getString("guild"),
                        GuildLogType.valueOf(resultSet.getString("type")),
                        resultSet.getString("action")
                );

                guild.addLog(guildLog);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Guild> loadAll() {
        Set<Guild> guilds = new HashSet<>();
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM kithard_guilds");
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {

                Set<UUID> deputies = CollectionSerializer.deserializeCollection(resultSet.getString("deputies"))
                        .stream()
                        .map(UUID::fromString)
                        .collect(Collectors.toSet());

                Guild guild = new Guild(
                        resultSet.getString("tag"),
                        resultSet.getString("name"),
                        UUID.fromString(resultSet.getString("owner")),
                        new GuildRegion(
                                LocationSerializer.deserialize(resultSet.getString("guild_region_center")),
                                resultSet.getInt("guild_region_size")
                        ),
                        LocationSerializer.deserialize(resultSet.getString("home")),
                        resultSet.getInt("lives"),
                        resultSet.getInt("points"),
                        resultSet.getInt("kills"),
                        resultSet.getInt("deaths"),
                        resultSet.getLong("expire_time"),
                        resultSet.getLong("create_time"),
                        resultSet.getLong("last_attack_time"),
                        resultSet.getLong("last_explode_time"),
                        resultSet.getBoolean("friendly_fire"),
                        resultSet.getBoolean("ally_fire"),
                        deputies,
                        CollectionSerializer.deserializeCollection(resultSet.getString("allies")),
                        ItemStackSerializer.itemStackArrayFromBase64(resultSet.getString("warehouse"))
                );

                guilds.add(guild);
            }
        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        this.logger.info("Pomyślnie załadowano: " + guilds.size() + " gildii.");
        return guilds;
    }

    @Override
    public void updateAll(Collection<Guild> toUpdate) {
        try (
                Connection connection = this.databaseService.getConnection();
                PreparedStatement guildStatement = connection.prepareStatement(UPDATE_GUILD);
                PreparedStatement memberStatement = connection.prepareStatement(UPDATE_MEMBER);
                PreparedStatement schemeStatement = connection.prepareStatement(UPDATE_SCHEME);
        ) {

            for (Guild data : toUpdate) {

                guildStatement.setString(1, data.getOwner().toString());
                guildStatement.setString(2, LocationSerializer.serialize(data.getRegion().getCenter().clone()));
                guildStatement.setInt(3, data.getRegion().getSize());
                guildStatement.setString(4, LocationSerializer.serialize(data.getHome()));
                guildStatement.setInt(5, data.getLives());
                guildStatement.setInt(6, data.getPoints());
                guildStatement.setInt(7, data.getKills());
                guildStatement.setInt(8, data.getDeaths());
                guildStatement.setLong(9, data.getExpireTime());
                guildStatement.setLong(10, data.getCreateTime());
                guildStatement.setLong(11, data.getLastAttackTime());
                guildStatement.setLong(12, data.getLastExplodeTime());
                guildStatement.setBoolean(13, data.isFriendlyFire());
                guildStatement.setBoolean(14, data.isAllyFire());
                guildStatement.setString(15, CollectionSerializer.serializeCollection(data.getDeputies()
                        .stream()
                        .map(UUID::toString)
                        .collect(Collectors.toSet())));
                guildStatement.setString(16, CollectionSerializer.serializeCollection(data.getAllies()));
                guildStatement.setString(17, ItemStackSerializer.itemStackArrayToBase64(data.getWarehouseContents()));
                guildStatement.setString(18, data.getTag());
                guildStatement.addBatch();

                for (GuildMember guildMember : data.getMembers()) {

                    memberStatement.setString(1, guildMember.getName());
                    memberStatement.setString(2, CollectionSerializer.serializeCollection(guildMember.getAllowedPermissions()
                            .stream()
                            .map(GuildPermission::toString)
                            .collect(Collectors.toSet())));
                    memberStatement.setString(3, guildMember.getUuid().toString());
                    memberStatement.addBatch();

                }

                for (GuildPermissionScheme guildPermissionScheme : data.getPermissionSchemes()) {

                    schemeStatement.setString(1, CollectionSerializer.serializeCollection(guildPermissionScheme.getAllowedPermissions()
                            .stream()
                            .map(GuildPermission::toString)
                            .collect(Collectors.toSet())));
                    schemeStatement.setString(2, guildPermissionScheme.getName());
                    schemeStatement.setString(3, data.getTag());
                    schemeStatement.addBatch();

                }

            }

            guildStatement.executeBatch();
            memberStatement.executeBatch();
            schemeStatement.executeBatch();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
