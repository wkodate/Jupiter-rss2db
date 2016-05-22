package com.wkodate.jupiter.rss2db.rss;

import java.util.Date;

/**
 * parseしたentryを格納するクラス.
 */
public class Item {

    private final String title;
    private final String link;
    private final String description;
    private final Date date;
    private final String image;
    private final int rssId;

    public Item(
            final String title,
            final String link,
            final String description,
            final Date date,
            final String image,
            final int rssId
    ) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
        this.image = image;
        this.rssId = rssId;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public int getRssId() {
        return rssId;
    }

}
