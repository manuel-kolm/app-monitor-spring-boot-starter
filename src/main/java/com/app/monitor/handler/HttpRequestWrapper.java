package com.app.monitor.handler;

import java.time.ZonedDateTime;

public class HttpRequestWrapper {

    private String internalId;
    private String path;
    private int statusCode;
    private String method;
    private ZonedDateTime started;
    private long duration;

    public String getInternalId() {
        return internalId;
    }

    public HttpRequestWrapper setInternalId(String internalId) {
        this.internalId = internalId;
        return this;
    }

    public String getPath() {
        return path;
    }

    public HttpRequestWrapper setPath(String path) {
        this.path = path;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpRequestWrapper setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public HttpRequestWrapper setMethod(String method) {
        this.method = method;
        return this;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public HttpRequestWrapper setStarted(ZonedDateTime started) {
        this.started = started;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public HttpRequestWrapper setDuration(long duration) {
        this.duration = duration;
        return this;
    }
}
