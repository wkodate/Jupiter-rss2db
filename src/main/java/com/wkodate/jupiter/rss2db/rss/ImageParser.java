package com.wkodate.jupiter.rss2db.rss;

import com.wkodate.jupiter.rss2db.opengraph.OpenGraph;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 顔像取得用パーサ.
 */
public class ImageParser {

    public static String parseOrg(final String rssLink) {
        try {
            OpenGraph og = new OpenGraph(rssLink, true);
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

    public static String parseFromContent(final String content) {
        Document document = Jsoup.parse(content, "UTF-8");
        Elements links = document.select(".t_h img");
        if (links.size() > 0) {
            return links.get(0).attr("src");
        }
        return "";
    }

}
