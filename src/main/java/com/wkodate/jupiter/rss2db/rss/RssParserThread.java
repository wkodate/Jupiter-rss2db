package com.wkodate.jupiter.rss2db.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * RSSをparseするThread.
 *
 * @author wkodate
 */
public class RssParserThread implements Callable<List<Item>> {

    private final int rssId;
    private final String rssUrl;
    private final long fetchIntervalMs;


    public RssParserThread(final Integer id, final String url, final long interval) {
        this.rssId = id;
        this.rssUrl = url;
        this.fetchIntervalMs = interval;
    }

    @Override
    public List<Item> call() throws Exception {
        List<Item> items = new ArrayList<>();
        try {
            // XML取得
            RssParser parser = new RssParser(rssId, new URL(rssUrl));
            List<SyndEntry> entries = parser.getEntries();
            for (SyndEntry entry : entries) {
                // parse
                items.add(parser.parse(entry));
                Thread.sleep(fetchIntervalMs);
            }
        } catch (FeedException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }
}
