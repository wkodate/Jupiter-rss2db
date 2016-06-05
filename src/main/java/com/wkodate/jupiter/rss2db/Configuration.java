package com.wkodate.jupiter.rss2db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 設定ファイルを扱うクラス.
 */
public class Configuration {

    private static final String DBTYPE_KEY = "dbtype";
    private static final String DBHOST_KEY = "dbhost";
    private static final String DBNAME_KEY = "dbname";
    private static final String DBUSER_KEY = "dbuser";
    private static final String DBPASSWORD_KEY = "dbpassword";

    private Properties prop = new Properties();

    public Configuration(final String filename) {
        try (FileInputStream fi = new FileInputStream(filename)){
            prop.load(fi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final String getDbType() {
        return prop.getProperty(DBTYPE_KEY);
    }

    public final String getDbHost() {
        return prop.getProperty(DBHOST_KEY);
    }

    public final String getDbName() {
        return prop.getProperty(DBNAME_KEY);
    }

    public final String getDbUser() {
        return prop.getProperty(DBUSER_KEY);
    }

    public final String getDbPassword() {
        return prop.getProperty(DBPASSWORD_KEY);
    }

}
