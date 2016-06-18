package com.wkodate.jupiter.rss2db;

import com.wkodate.jupiter.rss2db.db.DbClient;
import com.wkodate.jupiter.rss2db.rss.Item;
import com.wkodate.jupiter.rss2db.rss.RssParserThread;
import com.wkodate.jupiter.rss2db.sns.TwitterBot;
import org.apache.log4j.Logger;
import twitter4j.TwitterException;

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

    private final DbClient client;

    private final TwitterBot twitter;

    private final long fetchIntervalMs;

    private final int maxTweets;

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
        this.twitter = new TwitterBot(
                conf.getTwitterConsumerKey(),
                conf.getTwitterConsumerSecret(),
                conf.getTwitterAccessToken(),
                conf.getTwitterAccessTokenSecret()
        );
        this.fetchIntervalMs = conf.getFetchIntervalMs();
        this.maxTweets = conf.getMaxTweets();
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
        int insertCount = 0;
        for (Future<List<Item>> future : futures) {
            List<Item> newItems = new ArrayList<>();
            try {
                List<Item> parsed = future.get();
                for (Item item : parsed) {
                    // 既にdbに存在する場合はスキップ
                    if (client.itemExists(item.getLink())) {
                        continue;
                    }
                    newItems.add(item);
                }
                if (newItems.size() == 0) {
                    continue;
                }
                client.insert(newItems);
                insertCount += newItems.size();
                // post tweets
                postTweets(newItems);
            } catch (InterruptedException | ExecutionException | SQLException e) {
                LOG.error("Unexpected error. please check rssid=" + newItems.get(0).getRssId() + " ", e);
            }
        }
        if (insertCount == 0) {
            LOG.info("No items are updated.");
            return;
        }
        LOG.info(insertCount + " items are updated.");
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

    private void postTweets(final List<Item> items) {
        int currentTweets = 0;
        try {
            for (Item item : items) {
                // id取得
                int id = client.selectItemId(item.getLink());
                twitter.post(item.getTitle(), String.valueOf(id));
                currentTweets++;
                if (currentTweets >= maxTweets) {
                    break;
                }
            }
        } catch (TwitterException | SQLException e) {
            LOG.error("Unexpected: ", e);
        }
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
            LOG.error("Unexpected: ", e);
        }
    }

}
