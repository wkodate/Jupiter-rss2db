package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Db処理用のインターフェース.
 */
public abstract class DbClient {

    protected Connection conn;

    public DbClient(
            final String host,
            final String name,
            final String user,
            final String password
    ) throws SQLException {
        conn = DriverManager.getConnection(host, user, password);
    }

    public abstract void init() throws SQLException;

    public abstract void insert(final List<Item> items) throws SQLException;

    public abstract ResultSet selectRsses() throws SQLException;

    public abstract boolean itemExists(final String url) throws SQLException;

    public abstract void close() throws SQLException;

}
