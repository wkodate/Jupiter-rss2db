package com.wkodate.jupiter.rss2db.rss;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * RssParserThreadのテスト.
 */
public class RssParserThreadTest {

    @Test
    public void testCall() throws Exception {
        Integer id = 1;
        String url = "file://" + getClass().getResource("/rss10.xml").getPath();
        long interval = 200L;
        RssParserThread thread = new RssParserThread(id, url, interval);
        List<Item> items = thread.call();
        assertThat(items.size(), is(2));
    }

}