package com.wkodate.jupiter.rss2db;

import com.wkodate.jupiter.rss2db.db.DbClient;
import com.wkodate.jupiter.rss2db.rss.Item;
import com.wkodate.jupiter.rss2db.rss.RssParserThread;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Rssの情報をDBに格納.
 */
public class RssToDb {

    private static final Logger LOG = Logger.getLogger(RssToDb.class);

    private DbClient client;

    private final long fetchIntervalMs;

    private ExecutorService es;

    public RssToDb(final String filename) throws SQLException {
        Configuration conf = new Configuration(filename);
        this.client = new DbClient.Builder(
                conf.getDbType(),
                conf.getDbHost(),
                conf.getDbName(),
                conf.getDbUser(),
                conf.getDbPassword()
        ).build();
        this.fetchIntervalMs = conf.getFetchIntervalMs();
    }

    public final void init() throws SQLException {
        es = Executors.newCachedThreadPool();
        client.init();
    }

    public final void process() throws SQLException {
        // RSSのidとURLのMapを取得
        Map<Integer, String> rsses = getRssIdUrlMap();

        List<Future<List<Item>>> futures = new ArrayList<>();
        // rssごとに並列でパース
        for (int rssId : rsses.keySet()) {
            Future<List<Item>> future = es.submit(
                    new RssParserThread(rssId, rsses.get(rssId), fetchIntervalMs));
            futures.add(future);
        }
        List<Item> insertItems = new ArrayList<>();
        for (Future<List<Item>> future : futures) {
            try {
                List<Item> parsed = future.get();
                for (Item item : parsed) {
                    // 既にdbに存在する場合はスキップ
                    if (client.itemExists(item.getLink())) {
                        continue;
                    }
                    insertItems.add(item);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (insertItems.size() > 0) {
            client.insert(insertItems);
        }
        LOG.info(insertItems.size() + " items are updated.");
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
        es.shutdown();
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
