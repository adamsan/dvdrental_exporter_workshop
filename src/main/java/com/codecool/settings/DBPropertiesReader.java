package com.codecool.settings;

import com.codecool.App;

import java.io.IOException;
import java.util.Properties;

public class DBPropertiesReader {
    public Properties getProperties(String propertiesFileName) {
        Properties dbProperties = new Properties();
        try (var f = App.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            dbProperties.load(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dbProperties;
    }
}
