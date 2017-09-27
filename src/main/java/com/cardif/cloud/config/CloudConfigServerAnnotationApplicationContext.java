package com.cardif.cloud.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudConfigServerAnnotationApplicationContext extends AnnotationConfigWebApplicationContext {


    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return new CloudEnvironment(ApplicationNameReader.readSpringApplicationName());
    }


}
