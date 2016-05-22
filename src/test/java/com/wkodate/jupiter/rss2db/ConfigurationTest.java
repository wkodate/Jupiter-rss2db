package com.wkodate.jupiter.rss2db;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
    public void testGetDbHost() throws Exception {
        assertThat(conf.getDbHost(), is("host"));
    }

    @Test
    public void testGetDbUser() throws Exception {
        assertThat(conf.getDbUser(), is("user"));
    }

    @Test
    public void testGetDbPassword() throws Exception {
        assertThat(conf.getDbPassword(), is("pass"));
    }
}