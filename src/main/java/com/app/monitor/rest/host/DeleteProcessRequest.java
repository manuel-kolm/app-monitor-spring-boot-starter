package com.app.monitor.rest.host;

import java.time.LocalDateTime;

public class DeleteProcessRequest {

    private LocalDateTime created;
    private int exitCode;

    public LocalDateTime getCreated() {
        return created;
    }

    public DeleteProcessRequest setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public int getExitCode() {
        return exitCode;
    }

    public DeleteProcessRequest setExitCode(int exitCode) {
        this.exitCode = exitCode;
        return this;
    }
}
