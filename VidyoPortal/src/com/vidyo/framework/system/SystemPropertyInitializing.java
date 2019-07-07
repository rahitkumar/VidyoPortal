package com.vidyo.framework.system;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;

public class SystemPropertyInitializing implements InitializingBean {
    /** Properties to be set */
    private Map systemProperties;

    /** Sets the system properties */
    public void afterPropertiesSet() throws Exception {

        if (systemProperties == null || systemProperties.isEmpty()) {
            // No properties to initialize
            return;
        }

        for (Object o : systemProperties.keySet()) {
            String key = (String) o;
            String value = (String) systemProperties.get(key);

            System.setProperty(key, value);
        }

    }

    public void setSystemProperties(Map systemProperties) {
        this.systemProperties = systemProperties;
    }
}