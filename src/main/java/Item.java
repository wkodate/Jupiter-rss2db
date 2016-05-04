import java.util.Date;

/**
 * Created by wkodate on 2016/05/02.
 */
public class Item {

    private final String title;
    private final String link;
    private final String description;
    private final Date date;
    private final String image;
    private final String rssUrl;

    public Item(
            final String title,
            final String link,
            final String description,
            final Date date,
            final String image,
            final String rssUrl
    ) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
        this.image = image;
        this.rssUrl = rssUrl;

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

    public String getRssUrl() {
        return rssUrl;
    }
}
