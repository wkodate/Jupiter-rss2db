package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * db.DbClient
 */
public abstract class DbClient {

    private final String dbHost;
    private final String dbName;
    private final String dbUser;
    private final String dbPassword;

    protected Connection conn;

    public DbClient(
            final String host,
            final String name,
            final String user,
            final String password
    ) throws SQLException {
        this.dbHost = host;
        this.dbName = name;
        this.dbUser = user;
        this.dbPassword = password;
        conn = DriverManager.getConnection(host, user, password);
    }

    public abstract void init() throws SQLException;

    public abstract void insert(final List<Item> items) throws SQLException;

    public abstract void close() throws SQLException;

    public final Connection getConnection() {
        return conn;
    }

}
