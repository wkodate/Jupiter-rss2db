package com.wkodate.jupiter.rss2db.rss;

import org.junit.Before;
import org.junit.Test;

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
    public static final int RSS_ID = 2;

    private Item item;

    @Before
    public void setUp() throws Exception {
        item = new Item(TITLE, LINK, DESCRIPTION, DATE, IMAGE, RSS_ID, DATE, DATE);
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
        assertThat(item.getRssId(), is(RSS_ID));
    }

    @Test
    public void testGetCreatedAt() throws Exception {
        assertThat(item.getCreatedAt(), is(DATE));
    }

    @Test
    public void testGetUpdatedAt() throws Exception {
        assertThat(item.getUpdatedAt(), is(DATE));
    }
}