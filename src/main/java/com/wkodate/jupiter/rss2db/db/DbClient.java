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
            final String type,
            final String host,
            final String name,
            final String user,
            final String password
    ) throws SQLException {
        conn = DriverManager.getConnection(
                makeUrl(type, host, name),
                user,
                password);
    }

    private String makeUrl(String type, String host, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:");
        sb.append(type);
        sb.append("://");
        sb.append(host);
        sb.append("/");
        sb.append(name);
        return sb.toString();
    }

    public abstract void init() throws SQLException;

    public abstract void insert(final List<Item> items) throws SQLException;

    public abstract ResultSet selectRsses() throws SQLException;

    public abstract boolean itemExists(final String url) throws SQLException;

    public abstract void close() throws SQLException;

    public static class Builder {
        private final String type;
        private final String host;
        private final String name;
        private final String user;
        private final String password;

        public Builder(
                final String type,
                final String host,
                final String name,
                final String user,
                final String password) {
            this.type = type;
            this.host = host;
            this.name = name;
            this.user = user;
            this.password = password;
        }

        public DbClient build() throws SQLException {
            if ("mysql".equals(this.type)) {
                return new MySqlClient(
                        this.host,
                        this.name,
                        this.user,
                        this.password
                );
            }
            throw new IllegalArgumentException("Unexpected DB type: " + this.type);
        }
    }

}
