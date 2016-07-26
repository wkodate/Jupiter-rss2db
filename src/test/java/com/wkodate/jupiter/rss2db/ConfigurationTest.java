package com.wkodate.jupiter.rss2db;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Configurationのテスト.
 *
 * @author wkodate
 */
public class ConfigurationTest {

    private static final String filename = "test.properties";

    private Configuration conf;

    @Before
    public void setUp() {
        String path = getClass().getClassLoader().getResource(filename).getPath();
        conf = new Configuration(path);
    }

    @Test
    public void testGetDbType() throws Exception {
        assertThat(conf.getDbType(), is("mysql"));
    }

    @Test
    public void testGetDbHost() throws Exception {
        assertThat(conf.getDbHost(), is("host"));
    }

    @Test
    public void testGetDbName() throws Exception {
        assertThat(conf.getDbName(), is("name"));
    }

    @Test
    public void testGetDbUser() throws Exception {
        assertThat(conf.getDbUser(), is("user"));
    }

    @Test
    public void testGetDbPassword() throws Exception {
        assertThat(conf.getDbPassword(), is("pass"));
    }

    @Test
    public void testGetFetchIntervalMs() throws Exception {
        assertThat(conf.getFetchIntervalMs(), is(1000L));
    }

    @Test
    public void testPermitTweet() throws Exception {
        assertThat(conf.getPermiteTweet(), is(false));
    }

    @Test
    public void testGetTwitterConsumerKey() throws Exception {
        assertThat(conf.getTwitterConsumerKey(), is("conskey"));
    }

    @Test
    public void testGetTwitterConsumerSecret() throws Exception {
        assertThat(conf.getTwitterConsumerSecret(), is("conssecret"));
    }

    @Test
    public void testGetTwitterAccessToken() throws Exception {
        assertThat(conf.getTwitterAccessToken(), is("accesstoken"));
    }

    @Test
    public void testGetTwitterAccessTokenSecret() throws Exception {
        assertThat(conf.getTwitterAccessTokenSecret(), is("accesstokensecret"));
    }

    @Test
    public void testGetMaxTweet() throws Exception {
        assertThat(conf.getMaxTweets(), is(3));
    }
}