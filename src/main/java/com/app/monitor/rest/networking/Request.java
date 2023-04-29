package com.app.monitor.rest.networking;

import com.app.monitor.rest.system.ExceptionInfo;

import java.time.ZonedDateTime;

public class Request {

    private String id;
    private String path;
    private int statusCode;
    private String method;
    private ZonedDateTime started;
    private long duration;
    private ExceptionInfo exception;

    public String getId() {
        return id;
    }

    public Request setId(String id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Request setPath(String path) {
        this.path = path;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Request setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public ZonedDateTime getStarted() {
        return started;
    }

    public Request setStarted(ZonedDateTime started) {
        this.started = started;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public Request setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public ExceptionInfo getException() {
        return exception;
    }

    public Request setException(ExceptionInfo exception) {
        this.exception = exception;
        return this;
    }
}
