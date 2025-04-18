package me.doublenico.rm.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.doublenico.rm.configurations.DatabaseModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class MySQL implements IDatabase {

    private final HikariConfig config;
    private final DatabaseModel database;
    private HikariDataSource dataSource;

    public MySQL(DatabaseModel database) {
        this.database = database;
        config = new HikariConfig();
    }

    public MySQL connect() {
        createDatabaseIfNotExists();
        config.setJdbcUrl("jdbc:mysql://" + database.getHost() + ":" + database.getPort() + "/" + database.getDatabase());
        config.setUsername(database.getUsername());
        config.setPassword(database.getPassword());
        config.setMaximumPoolSize(database.getPoolSize());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        return this;
    }

    private void createDatabaseIfNotExists() {
        String jdbcUrl = "jdbc:mysql://" + database.getHost() + ":" + database.getPort();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, database.getUsername(), database.getPassword());
             Statement statement = connection.createStatement()) {

            String createDbQuery = "CREATE DATABASE IF NOT EXISTS `" + database.getDatabase() + "`";
            statement.executeUpdate(createDbQuery);
            System.out.println("Database created or verified successfully.");

        } catch (SQLException e) {
            throw new RuntimeException("Unable to create database: " + database.getDatabase(), e);
        }
    }

    public MySQL disconnect() {
        if (dataSource != null) {
            dataSource.close();
        }
        return this;
    }

    public boolean isConnected() {
        if (dataSource != null) {
            return !dataSource.isClosed();
        }
        return false;
    }

    public MySQL createDatabase(String sqlFile) {
        String sqlFilePath = "src/main/resources/" + sqlFile;

        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
            String[] sqlStatements = sql.split(";");
            for (String sqlStatement : sqlStatements)
                //TODO: duplicate
                if (!sqlStatement.trim().isEmpty()) statement.execute(sqlStatement.trim());

            System.out.println("Database tables created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create tables", e);
        } catch (IOException e) {
            throw new RuntimeException("Unable to get sql file", e);
        }
        return this;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public DatabaseModel getDatabase() {
        return database;
    }

    public HikariConfig getConfig() {
        return config;
    }
}
