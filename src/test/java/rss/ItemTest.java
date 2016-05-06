package rss;

import org.junit.Before;
import org.junit.Test;
import rss.Item;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Itemのテスト.
 */
public class ItemTest {

    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final Date DATE = new Date();
    public static final String IMAGE = "image";
    public static final String RSS_URL = "rss_url";

    private Item item;

    @Before
    public void setUp() throws Exception {
        item = new Item(TITLE, LINK, DESCRIPTION, DATE, IMAGE, RSS_URL);
    }

    @Test
    public void testGetTitle() throws Exception {
        assertThat(item.getTitle(), is(TITLE));
    }

    @Test
    public void testGetLink() throws Exception {
        assertThat(item.getLink(), is(LINK));
    }

    @Test
    public void testGetDescription() throws Exception {
        assertThat(item.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void testGetDate() throws Exception {
        assertThat(item.getDate(), is(DATE));
    }

    @Test
    public void testGetImage() throws Exception {
        assertThat(item.getImage(), is(IMAGE));
    }

    @Test
    public void testGetRssUrl() throws Exception {
        assertThat(item.getRssUrl(), is(RSS_URL));
    }
}