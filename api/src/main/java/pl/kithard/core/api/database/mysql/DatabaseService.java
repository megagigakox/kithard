package pl.kithard.core.api.database.mysql;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseService {

    private final HikariDataSource hikariDataSource;

    public DatabaseService(String host, int port, String database, String username, String password) {
        this.hikariDataSource = new HikariDataSource();

        this.hikariDataSource.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        this.hikariDataSource.setUsername(username);
        this.hikariDataSource.setPassword(password);
        this.hikariDataSource.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);

        this.hikariDataSource.addDataSourceProperty("cachePrepStmts", true);
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.hikariDataSource.addDataSourceProperty("useServerPrepStmts", true);
    }

    public void shutdown() {
        hikariDataSource.close();
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }
}
