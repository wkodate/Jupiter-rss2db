package com.wkodate.jupiter.rss2db.sns;

import org.apache.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * Twitterに記事を投稿するクラス.
 */
public class TwitterBot {

    private static final Logger LOG = Logger.getLogger(TwitterBot.class);

    private final Twitter twitter;

    public TwitterBot(
            final String consKey,
            final String consSecret,
            final String token,
            final String tokenSecret
    ) {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(consKey, consSecret);
        AccessToken accessToken = new AccessToken(token, tokenSecret);
        twitter.setOAuthAccessToken(accessToken);
        if (!twitter.getAuthorization().isEnabled()) {
            LOG.warn("OAuth consumer key/secret is not set.");
            throw new IllegalStateException();
        }
    }

    public void post(final String title, final String url) throws TwitterException {
        twitter.updateStatus(title + "\n" + url);
    }

}
