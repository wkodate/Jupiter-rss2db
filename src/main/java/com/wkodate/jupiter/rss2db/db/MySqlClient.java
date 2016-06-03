package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MySQL
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
    public void close() throws SQLException {
        conn.close();
    }

    public Map<Integer, String> getIdUrlMap() throws SQLException {
        Map<Integer, String> idUrl = new HashMap<>();

        Statement statement = conn.createStatement();
        String sql = creator.createStatementThatSelectRssesTable();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String url = rs.getString("rss_url");
            idUrl.put(id, url);
        }
        return idUrl;
    }

}
