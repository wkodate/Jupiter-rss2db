package com.wkodate.jupiter.rss2db.opengraph;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * OpenGraphのテスト.
 */
public class OpenGraphTest {
    private static final String FILE_PATH = "/opengraph.html";

    @Test
    public final void parseTest() throws Exception {
        String url = getClass().getResource(FILE_PATH).toString();

        OpenGraph og = new OpenGraph(url, true);
        String title = og.getContent("title");
        String type = og.getContent("type");
        assertThat(title, is("Yahoo! JAPAN"));
        assertThat(type, is("article"));
    }

}
