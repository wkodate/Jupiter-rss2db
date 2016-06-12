package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * MySQLクライアント.
 */
public class MySqlClient extends DbClient {

    private static final String DB_TYPE = "mysql";

    private MySqlStatementCreator creator;

    public MySqlClient(String host, String name, String user, String password) throws SQLException {
        super(DB_TYPE, host, name, user, password);
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
        // TODO statementのclose
    }

    @Override
    public ResultSet selectRsses() throws SQLException {
        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatSelectRssesTable();
        return statement.executeQuery(sql);
    }

    @Override
    public int selectItemId(String url) throws SQLException {
        ResultSet rs = selectId(url);
        rs.next();
        return rs.getInt("id");
    }

    @Override
    public boolean itemExists(String url) throws SQLException {
        ResultSet rs = selectId(url);
        rs.last();
        return rs.getRow() >= 1;
    }

    private ResultSet selectId(String url) throws SQLException {
        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatSelectIdFromItemsTable(url);
        return statement.executeQuery(sql);
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }

}
