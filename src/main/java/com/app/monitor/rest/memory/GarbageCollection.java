package com.app.monitor.rest.memory;

import java.util.Map;

public class GarbageCollection {

    private long id;
    private String name;
    private String cause;
    private long duration;
    private Map<String, MemoryUsage> memoryUsageBeforeGc;
    private Map<String, MemoryUsage> memoryUsageAfterGc;

    public long getId() {
        return id;
    }

    public GarbageCollection setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GarbageCollection setName(String name) {
        this.name = name;
        return this;
    }

    public String getCause() {
        return cause;
    }

    public GarbageCollection setCause(String cause) {
        this.cause = cause;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public GarbageCollection setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public Map<String, MemoryUsage> getMemoryUsageBeforeGc() {
        return memoryUsageBeforeGc;
    }

    public GarbageCollection setMemoryUsageBeforeGc(Map<String, MemoryUsage> memoryUsageBeforeGc) {
        this.memoryUsageBeforeGc = memoryUsageBeforeGc;
        return this;
    }

    public Map<String, MemoryUsage> getMemoryUsageAfterGc() {
        return memoryUsageAfterGc;
    }

    public GarbageCollection setMemoryUsageAfterGc(Map<String, MemoryUsage> memoryUsageAfterGc) {
        this.memoryUsageAfterGc = memoryUsageAfterGc;
        return this;
    }
}
