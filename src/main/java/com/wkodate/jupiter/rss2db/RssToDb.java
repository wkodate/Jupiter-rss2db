package com.wkodate.jupiter.rss2db;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import com.wkodate.jupiter.rss2db.db.DbClient;
import com.wkodate.jupiter.rss2db.rss.Item;
import com.wkodate.jupiter.rss2db.rss.RssParser;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rssの情報をDBに格納.
 */
public class RssToDb {

    private DbClient client;

    public RssToDb(final String filename) throws SQLException {
        Configuration conf = new Configuration(filename);
        this.client = new DbClient.Builder(
                conf.getDbType(),
                conf.getDbHost(),
                conf.getDbName(),
                conf.getDbUser(),
                conf.getDbPassword()
        ).build();
    }

    public final void init() throws SQLException {
        client.init();
    }

    public final void process() throws SQLException {
        // RSSのidとURLのMapを取得
        Map<Integer, String> rsses = getRssIdUrlMap();

        // TODO rss idごとに並列にパース
        for (Integer id : rsses.keySet()) {
            try {
                List<Item> items = new ArrayList<>();
                // parse
                RssParser parser = new RssParser(Integer.valueOf(id), new URL(rsses.get(id)));
                List<SyndEntry> entries = parser.getEntries();
                for (SyndEntry entry : entries) {
                    if (client.itemExists(entry.getLink())) {
                        continue;
                    }
                    items.add(parser.parse(entry));
                    Thread.sleep(1000L);
                }
                // to mysql
                if (items.size() == 0) {
                    continue;
                }
                client.insert(items);
            } catch (FeedException | IOException | SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<Integer, String> getRssIdUrlMap() {
        Map<Integer, String> idUrl = new HashMap<>();
        try (ResultSet rs = client.selectRsses()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("rss_url");
                idUrl.put(id, url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idUrl;

    }

    public final void close() throws SQLException {
        client.close();
    }

    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -cp rss2db.jar com.wkodate.jupiter.rss2db.RssToDb <configuration file path>");
            System.exit(1);
        }
        String filename = args[0];
        try {
            RssToDb rssToDb = new RssToDb(filename);
            rssToDb.init();
            rssToDb.process();
            rssToDb.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
