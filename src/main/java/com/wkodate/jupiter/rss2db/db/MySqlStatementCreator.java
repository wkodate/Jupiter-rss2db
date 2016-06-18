package com.wkodate.jupiter.rss2db.db;

/**
 * MySQLのstatementを作成するクラス.
 */
public class MySqlStatementCreator {

    private final String dbname;

    public MySqlStatementCreator(final String dbname) {
        this.dbname = dbname;
    }

    public final String createStatementThatSelectRssesTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id, rss_url, title, site_link, description, created_at, updated_at FROM ");
        sb.append(dbname);
        sb.append(".rsses;");
        return sb.toString();
    }

    public final String createStatementThatInsertIntoItemsTable(final int size) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(dbname);
        sb.append(".items(link, title, description, date, image, rss_id, created_at, updated_at) VALUES ");
        for (int i = 0; i < size; i++) {
            sb.append("(?, ?, ?, ?, ?, ?, ?, ?)");
            if (i < size - 1) {
                sb.append(",");
            }
        }
        sb.append(";");
        return sb.toString();
    }

    public final String createStatementThatSelectIdFromItemsTable(final String link) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id FROM items WHERE link='");
        sb.append(link);
        sb.append("';");
        return sb.toString();
    }

}
