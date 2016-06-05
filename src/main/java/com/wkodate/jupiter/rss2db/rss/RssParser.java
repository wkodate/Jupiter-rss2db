package com.wkodate.jupiter.rss2db.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.wkodate.jupiter.rss2db.opengraph.OpenGraph;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * RssをParseするクラス.
 *
 * @author wkodate
 */
public class RssParser {

    private final int id;
    private final URL url;

    private SyndFeed feed;

    public RssParser(final int id, final URL url) throws IOException, FeedException {
        this.id = id;
        this.url = url;
        SyndFeedInput input = new SyndFeedInput();
        this.feed = input.build(new XmlReader(url));
    }

    public List<SyndEntry> getEntries() {
        return feed.getEntries();
    }

    /**
     * entry毎のparse処理.
     *
     * @param entry SyndEntry
     * @return parseしたItem.
     */
    public final Item parse(final SyndEntry entry) {
        Date date = entry.getPublishedDate();
        // Atomの場合はupdatedが必須なのでそちらを利用
        if (date == null) {
            date = entry.getUpdatedDate();
        }
        String image = parseImage(entry.getLink());
        return new Item(
                entry.getTitle(),
                entry.getLink(),
                entry.getDescription().getValue(),
                date,
                image,
                id,
                new Date(),
                new Date()
        );
    }

    private String parseImage(final String link) {
        try {
            OpenGraph og = new OpenGraph(link, true);
            String image = og.getContent("image");
            if (image == null) {
                return "";
            }
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
