package com.app.monitor.rest.system;

import java.util.List;

public class ExceptionInfo {

    private String classId;
    private String message;
    private List<String> stackTrace;

    public String getClassId() {
        return classId;
    }

    public ExceptionInfo setClassId(String classId) {
        this.classId = classId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public ExceptionInfo setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }
}
