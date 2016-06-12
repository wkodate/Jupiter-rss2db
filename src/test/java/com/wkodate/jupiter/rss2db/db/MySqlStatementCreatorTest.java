package com.wkodate.jupiter.rss2db.db;

import com.wkodate.jupiter.rss2db.rss.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * MySqlStatementCreatorのテスト.
 */
public class MySqlStatementCreatorTest {


    private static final String TEST_DB_NAME = "testdb";
    private final String TITLE1 = "title1";
    private final String LINK1 = "link1";
    private final String DESC1 = "desc1";
    private final String IMAGE1 = "image1";
    private final Date DATE1;
    {
        // 2016/05/29 12:34:56
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, 2016);
        cal1.set(Calendar.MONTH, Calendar.MAY);
        cal1.set(Calendar.DAY_OF_MONTH, 29);
        cal1.set(Calendar.HOUR_OF_DAY, 12);
        cal1.set(Calendar.MINUTE, 34);
        cal1.set(Calendar.SECOND, 56);
        DATE1 = cal1.getTime();
    }
    private final int RSSID1 = 1;

    private final String TITLE2 = "title2";
    private final String LINK2 = "link2";
    private final String DESC2 = "desc2";
    private final String IMAGE2 = "image2";
    private final Date DATE2;
    {
        // 2016/05/27 23:45:01
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, 2016);
        cal2.set(Calendar.MONTH, Calendar.MAY);
        cal2.set(Calendar.DAY_OF_MONTH, 27);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 45);
        cal2.set(Calendar.SECOND, 1);
        DATE2 = cal2.getTime();
    }
    private final int RSSID2 = 2;

    private List<Item> items;

    @Before
    public void setUp() throws Exception {
        items = new ArrayList<>();
        Item item1 = new Item(TITLE1, LINK1, DESC1, DATE1, IMAGE1, RSSID1, DATE1, DATE1);
        Item item2 = new Item(TITLE2, LINK2, DESC2, DATE2, IMAGE2, RSSID2, DATE2, DATE2);
        items.add(item1);
        items.add(item2);
    }

    @Test
    public void testCreateStatementThatInsertIntoItemsTable() throws Exception {
        MySqlStatementCreator creator = new MySqlStatementCreator(TEST_DB_NAME);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(TEST_DB_NAME);
        sb.append(".items(link, title, description, date, image, rss_id, created_at, updated_at) VALUES ('");
        sb.append(LINK1);
        sb.append("', '");
        sb.append(TITLE1);
        sb.append("', '");
        sb.append(DESC1);
        sb.append("', '");
        sb.append("2016-05-29 12:34:56");
        sb.append("', '");
        sb.append(IMAGE1);
        sb.append("', '");
        sb.append(RSSID1);
        sb.append("', '");
        sb.append("2016-05-29 12:34:56");
        sb.append("', '");
        sb.append("2016-05-29 12:34:56");
        sb.append("'),('");
        sb.append(LINK2);
        sb.append("', '");
        sb.append(TITLE2);
        sb.append("', '");
        sb.append(DESC2);
        sb.append("', '");
        sb.append("2016-05-27 23:45:01");
        sb.append("', '");
        sb.append(IMAGE2);
        sb.append("', '");
        sb.append(RSSID2);
        sb.append("', '");
        sb.append("2016-05-27 23:45:01");
        sb.append("', '");
        sb.append("2016-05-27 23:45:01");
        sb.append("');");
        assertThat(creator.createStatementThatInsertIntoItemsTable(items),
                is(sb.toString()));
    }

    @Test
    public void testCreateStatementThatSelectLinkFromItemsTable() {
        final String LINK = "http://www.link.com";
        MySqlStatementCreator creator = new MySqlStatementCreator(TEST_DB_NAME);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT id FROM items WHERE link='");
        sb.append(LINK);
        sb.append("';");
        assertThat(creator.createStatementThatSelectIdFromItemsTable(LINK),
                is(sb.toString()));

    }

}