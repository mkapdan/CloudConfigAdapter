package com.cardif.cloud.config;

import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.StandardServletEnvironment;


public class CloudEnvironment extends StandardServletEnvironment {

    @Override
    protected void customizePropertySources(MutablePropertySources propertySources) {
        super.customizePropertySources(propertySources);

        String oldSystemPropertyAppName=System.getProperty(CloudConfigConstants.CONFIG_SERVER_APP_NAME);
        try {

            if (Boolean.TRUE.toString().equalsIgnoreCase(this.getProperty(CloudConfigConstants.CONFIG_SERVER_ENABLE, CloudConfigConstants.CONFIG_SERVER_DEFAULT_ENABLE))) {
                System.setProperty(CloudConfigConstants.CONFIG_SERVER_APP_NAME,ApplicationNameReader.readSpringApplicationName());
                PropertySource<?> propertySource = initConfigServicePropertySourceLocator(this);
                if (null != propertySource) {
                    propertySources.addLast(propertySource);
                }
            }

        } catch (Exception ex) {

            System.setProperty(CloudConfigConstants.CONFIG_SERVER_APP_NAME,oldSystemPropertyAppName);
            if (Boolean.TRUE.toString().equalsIgnoreCase(this.getProperty(CloudConfigConstants.CONFIG_SERVER_FAIL_FAST, CloudConfigConstants.CONFIG_SERVER_DEFAULT_FAIL_FAST))) {
                throw new RuntimeException("Could not connect to the config server!!!");

            }
            logger.warn("failed to initialize cloud config environment", ex);


        }
    }

    private PropertySource<?> initConfigServicePropertySourceLocator(Environment environment) {

        ConfigClientProperties configClientProperties = new ConfigClientProperties(environment);

        configClientProperties.setUri(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_ADDRESS, CloudConfigConstants.CONFIG_SERVER_DEFAULT_ADDRESS));
        configClientProperties.setName(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_APP_NAME, CloudConfigConstants.CONFIG_SERVER_DEFAULT_APP_NAME));
        configClientProperties.setLabel(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_BRANCH, CloudConfigConstants.CONFIG_SERVER_DEFAULT_BRANCH));
        configClientProperties.setUsername(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_USERNAME, CloudConfigConstants.CONFIG_SERVER_DEFAULT_USERNAME));
        configClientProperties.setPassword(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_PASSWORD, CloudConfigConstants.CONFIG_SERVER_DEFAULT_PASSWORD));
        configClientProperties.setProfile(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_PROFILE, CloudConfigConstants.CONFIG_SERVER_DEFAULT_PROFILE));
        configClientProperties.setFailFast(!CloudConfigConstants.CONFIG_SERVER_DEFAULT_FAIL_FAST.equalsIgnoreCase(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_FAIL_FAST, CloudConfigConstants.CONFIG_SERVER_DEFAULT_FAIL_FAST)));
        configClientProperties.setFailFast(!CloudConfigConstants.CONFIG_SERVER_DEFAULT_ENABLE.equalsIgnoreCase(environment.getProperty(CloudConfigConstants.CONFIG_SERVER_ENABLE, CloudConfigConstants.CONFIG_SERVER_DEFAULT_ENABLE)));
        ConfigServicePropertySourceLocator configServicePropertySourceLocator = new ConfigServicePropertySourceLocator(configClientProperties);

        return configServicePropertySourceLocator.locate(environment);
    }

  }
