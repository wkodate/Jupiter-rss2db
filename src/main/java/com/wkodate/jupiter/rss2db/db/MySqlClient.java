package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * MySQLクライアント.
 */
public class MySqlClient extends DbClient {

    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        String sql = creator.createStatementThatInsertIntoItemsTable(items.size());
        PreparedStatement stmt = conn.prepareStatement(sql);
        for (int i = 0; i < items.size(); i++) {
            int base = i * 8;
            stmt.setString(base + 1, items.get(i).getLink());
            stmt.setString(base + 2, items.get(i).getTitle());
            stmt.setString(base + 3, items.get(i).getDescription());
            stmt.setString(base + 4, DF.format(items.get(i).getDate()));
            stmt.setString(base + 5, items.get(i).getImage());
            stmt.setInt(base + 6, items.get(i).getRssId());
            stmt.setString(base + 7, DF.format(items.get(i).getCreatedAt()));
            stmt.setString(base + 8, DF.format(items.get(i).getUpdatedAt()));
        }

        stmt.executeUpdate();
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
