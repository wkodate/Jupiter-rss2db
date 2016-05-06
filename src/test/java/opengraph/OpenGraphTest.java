package opengraph;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * OpenGraphのテスト.
 */
public class OpenGraphTest {
    private static final String FILE_PATH = "/opengraph.html";

    @Test
    public final void test() throws Exception {
        String url = getClass().getResource(FILE_PATH).toString();

        OpenGraph testPage = new OpenGraph(url, true);
        String title = testPage.getContent("title");
        String type = testPage.getContent("type");
        assertThat(title, is("Yahoo! JAPAN"));
        assertThat(type, is("article"));
    }

}
