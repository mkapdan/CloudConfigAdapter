package com.cardif.cloud.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudConfigServerWebApplicationContext extends XmlWebApplicationContext {
    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return new CloudEnvironment(ApplicationNameReader.readSpringApplicationName());
    }
}