package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * MySQLクライアント.
 */
public class MySqlClient extends DbClient {

    private MySqlStatementCreator creator;

    public MySqlClient(String host, String name, String user, String password) throws SQLException {
        super(host, name, user, password);
        creator = new MySqlStatementCreator(name);
    }

    @Override
    public void init() throws SQLException {
    }

    @Override
    public void insert(List<Item> items) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatInsertIntoItemsTable(items);
        statement.executeUpdate(sql);
    }

    @Override
    public ResultSet selectRsses() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatSelectRssesTable();
        return statement.executeQuery(sql);
    }

    @Override
    public boolean itemExists(String url) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatSelectRssesTable();
        ResultSet rs = statement.executeQuery(sql);
        return rs.getInt(1) == 1;
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

}
