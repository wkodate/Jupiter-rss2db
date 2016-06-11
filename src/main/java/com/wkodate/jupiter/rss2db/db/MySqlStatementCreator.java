package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * MySQLのstatementを作成するクラス.
 */
public class MySqlStatementCreator {

    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public final String createStatementThatInsertIntoItemsTable(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(dbname);
        sb.append(".items(link, title, description, date, image, rss_id, created_at, updated_at) VALUES ");
        for (int i = 0; i < items.size(); i++) {
            sb.append("('");
            sb.append(items.get(i).getLink());
            sb.append("', '");
            sb.append(items.get(i).getTitle());
            sb.append("', '");
            sb.append(items.get(i).getDescription());
            sb.append("', '");
            sb.append(DF.format(items.get(i).getDate()));
            sb.append("', '");
            sb.append(items.get(i).getImage());
            sb.append("', '");
            sb.append(items.get(i).getRssId());
            sb.append("', '");
            sb.append(DF.format(items.get(i).getCreatedAt()));
            sb.append("', '");
            sb.append(DF.format(items.get(i).getUpdatedAt()));
            sb.append("')");
            if (i < items.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(";");
        return sb.toString();
    }

    public final String createStatementThatSelectLinkFromItemsTable(final String link) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM items WHERE link=");
        sb.append(link);
        sb.append(";");
        return sb.toString();
    }

}
