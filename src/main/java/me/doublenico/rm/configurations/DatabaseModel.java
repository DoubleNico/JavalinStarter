package me.doublenico.rm.configurations;

public class DatabaseModel {
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;
    private int poolSize;

    public DatabaseModel() {}

    public DatabaseModel(String host, String port, String database, String username, String password, int poolSize) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
