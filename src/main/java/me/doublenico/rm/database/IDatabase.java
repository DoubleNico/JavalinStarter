package me.doublenico.rm.database;

public interface IDatabase {

    IDatabase connect();

    IDatabase disconnect();

    boolean isConnected();

    IDatabase createDatabase(String sqlFile);

}
