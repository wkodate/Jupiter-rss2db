package com.wkodate.jupiter.rss2db.rss;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.wkodate.jupiter.rss2db.opengraph.OpenGraph;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RssをParseするクラス.
 *
 * @author wkodate
 */
public class RssParser {

    private final URL url;

    public RssParser(final URL url) throws MalformedURLException {
        this.url = url;
    }

    /**
     * XMLをparse
     *
     * @return parseしたItemのリスト.
     * @throws IOException
     * @throws FeedException
     */
    public final List<Item> parse() throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        String rssUrl = feed.getLink();

        List<Item> items = new ArrayList<>();
        for (Object obj : feed.getEntries()) {
            Item item = parseEntry((SyndEntry) obj, rssUrl);
            items.add(item);
        }
        return items;
    }

    /**
     * entry毎のparse処理.
     *
     * @param syndEntry SyndEntry
     * @param rssUrl    RSSのURL
     * @return parse結果を格納したItemオブジェクト.
     */
    private Item parseEntry(final SyndEntry syndEntry, final String rssUrl) {
        Date date = syndEntry.getPublishedDate();
        // Atomの場合はupdatedが必須なのでそちらを利用
        if (date == null) {
            date = syndEntry.getUpdatedDate();
        }
        String image = parseImage(syndEntry.getLink());
        return new Item(
                syndEntry.getTitle(),
                syndEntry.getLink(),
                syndEntry.getDescription().getValue(),
                date,
                image,
                rssUrl
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
