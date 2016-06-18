package com.wkodate.jupiter.rss2db.rss;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

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

    private SyndFeed feed;

    public RssParser(final int id, final URL url) throws IOException, FeedException {
        this.id = id;
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

        // parse image
        String image = ImageParser.parseOrg(entry.getLink());
        if ("".equals(image) && entry.getContents().size() > 0) {
            String content = ((SyndContent) entry.getContents().get(0)).getValue();
            image = ImageParser.parseFromContent(content);
        }

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

}
