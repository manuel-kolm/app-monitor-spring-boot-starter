package com.app.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.monitor")
public class AppMonitorConfigProperties {

    private String name;
    private boolean trackLocalRequests = true;

    public String getName() {
        return name;
    }

    public AppMonitorConfigProperties setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isTrackLocalRequests() {
        return trackLocalRequests;
    }

    public AppMonitorConfigProperties setTrackLocalRequests(boolean trackLocalRequests) {
        this.trackLocalRequests = trackLocalRequests;
        return this;
    }
}
