package com.cardif.cloud.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class CloudConfigServerAnnotationApplicationContext extends AnnotationConfigWebApplicationContext {

    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return new CloudEnvironment();
    }

}
