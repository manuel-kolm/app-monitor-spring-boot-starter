package com.app.monitor.rest.host;


import com.app.monitor.rest.system.OperatingSystem;

import java.time.ZonedDateTime;

public class CreateProcessRequest {

    private ZonedDateTime created;
    private String name;
    private OperatingSystem operatingSystem;

    public ZonedDateTime getCreated() {
        return created;
    }

    public CreateProcessRequest setCreated(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateProcessRequest setName(String name) {
        this.name = name;
        return this;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public CreateProcessRequest setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }
}
