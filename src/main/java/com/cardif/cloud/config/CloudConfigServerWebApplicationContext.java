package com.cardif.cloud.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class CloudConfigServerWebApplicationContext extends XmlWebApplicationContext {
    @Override
    protected ConfigurableEnvironment createEnvironment() {
        return new CloudEnvironment();
    }
}