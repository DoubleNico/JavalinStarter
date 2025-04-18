package me.doublenico.rm.database;


import me.doublenico.rm.utils.FileManager;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Local implements IDatabase {

    private String url;
    private Connection connection;

    public Local connect() {
        File folder = new File(new FileManager().getInstallationFolder().getPath() + "/database.db");
        if (!folder.exists()) {
            System.out.println("Database does not exist, creating...");
            try {
                if (folder.createNewFile()) System.out.println("Database created!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        url = "jdbc:sqlite:" + folder;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            if (!areTablesCreated()) {
                System.out.println("Tables do not exist, creating...");
                createDatabase("");
            }
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException) System.out.println("Could not find SQLITE database");
            throw new RuntimeException(e);
        }
        return this;
    }

    public Local createDatabase(String sqlFile) {
        try {
            checkConnection();
            try (Statement statement = connection.createStatement()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private boolean areTablesCreated() {
        String checkTables = "SELECT name FROM sqlite_master WHERE type='table' AND name='Members' OR name='MemberIPs'";
        try {
            checkConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(checkTables)) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConnected() {
        try {
            return connection.isValid(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Local disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void checkConnection() throws SQLException {
        boolean renew = false;
        if (this.connection == null) renew = true;
        else if (this.connection.isClosed()) renew = true;
        if (renew) this.connection = DriverManager.getConnection(url);
    }
}