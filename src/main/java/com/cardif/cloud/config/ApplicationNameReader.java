package com.cardif.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ApplicationNameReader {

    static Logger logger=LoggerFactory.getLogger(ApplicationNameReader.class);

    public static String readSpringApplicationName()  {
        try {
            Properties prop = new Properties();
            InputStream input= CloudConfigConstants.class.getClassLoader().getResourceAsStream(CloudConfigConstants.CLOUD_CONFIG_ADAPTER_PROP_FILE_NAME);
            prop.load(input);
            return prop.getProperty(CloudConfigConstants.CONFIG_SERVER_APP_NAME);
        } catch (IOException e) {
            logger.warn("failed to read application name", e);
        }
        return null;
    }

}
