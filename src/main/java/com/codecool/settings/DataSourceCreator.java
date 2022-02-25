package com.codecool.settings;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceCreator {
    public DataSource getDataSource(Properties dbProperties) {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(dbProperties.getProperty("db.url"));
        ds.setUser(dbProperties.getProperty("db.user"));
        ds.setPassword(dbProperties.getProperty("db.password"));
        return ds;
    }
}
