package com.wkodate.jupiter.rss2db.rss;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * ImageParserのテスト.
 */
public class ImageParserTest {

    private static final String RDF1 = "/imgParser1.rdf";
    private static final String RDF2 = "/imgParser2.rdf";

    @Test
    public void testParseImageFromContent() throws Exception {
        final String expected1 = "http://livedoor.blogimg.jp/kamillejin-denabay/imgs/a/9/a9618bda-s.jpg";
        final String expected2 = "http://livedoor.blogimg.jp/baynewsflash/imgs/e/6/e6ff5d19.jpg";

        assertThat(ImageParser.parseFromContent(getContent(RDF1)), is(expected1));
        assertThat(ImageParser.parseFromContent(getContent(RDF2)), is(expected2));
    }

    private String getContent(final String rdf) throws Exception {
        SyndFeedInput input = new SyndFeedInput();
        URL url = getClass().getResource(rdf);
        SyndFeed feed = input.build(new XmlReader(url));
        SyndEntry entry = (SyndEntry) feed.getEntries().get(0);
        return ((SyndContent) entry.getContents().get(0)).getValue();
    }

}