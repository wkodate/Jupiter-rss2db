package com.wkodate.jupiter.rss2db.rss;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * RssParserのTest.
 *
 * @author wkodate
 */
public class RssParserTest {

    @Test
    public final void rss10Test() throws Exception {
        final int ID = 1;
        URL url = getClass().getResource("/rss10.xml");
        RssParser parser = new RssParser(ID, url);
        List<Item> actual = parser.parse();

        assertThat(actual.get(0).getTitle(), is("title1"));
        assertThat(actual.get(0).getLink(), is("http://www.url1.com"));
        assertThat(actual.get(0).getDescription(), is("This is a first description."));
        assertThat(actual.get(0).getDate().toString(), is("Wed May 04 16:00:02 JST 2016"));
        assertThat(actual.get(0).getImage(), is(""));
        assertThat(actual.get(0).getRssId(), is(ID));
    }

    @Test
    public final void rss20Test() throws Exception {
        final int ID = 2;
        URL url = getClass().getResource("/rss20.xml");
        RssParser parser = new RssParser(ID, url);
        List<Item> actual = parser.parse();

        assertThat(actual.get(0).getTitle(), is("title1"));
        assertThat(actual.get(0).getLink(), is("http://link1.co.jp"));
        assertThat(actual.get(0).getDescription(), is("I like a description."));
        assertThat(actual.get(0).getDate().toString(), is("Wed Jun 11 15:30:59 JST 2008"));
        assertThat(actual.get(0).getImage(), is(""));
        assertThat(actual.get(0).getRssId(), is(ID));
    }

    @Test
    public final void atomTest() throws Exception {
        final int ID = 3;
        URL url = getClass().getResource("/atom.xml");
        RssParser parser = new RssParser(ID, url);
        List<Item> actual = parser.parse();

        assertThat(actual.get(0).getTitle(), is("sugoi taitoru"));
        assertThat(actual.get(0).getLink(), is("http://yafoo.co.jp/1"));
        assertThat(actual.get(0).getDescription(), is("youyaku"));
        assertThat(actual.get(0).getDate().toString(), is("Tue Jan 01 00:00:00 JST 2002013"));
        assertThat(actual.get(0).getImage(), is(""));
        assertThat(actual.get(0).getRssId(), is(ID));
    }
}
