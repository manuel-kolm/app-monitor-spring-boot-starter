package com.app.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.monitor")
public class AppMonitorConfigProperties {

    private String name;

    public String getName() {
        return name;
    }

    public AppMonitorConfigProperties setName(String name) {
        this.name = name;
        return this;
    }
}
