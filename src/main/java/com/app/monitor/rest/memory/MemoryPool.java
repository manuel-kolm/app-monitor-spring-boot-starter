package com.app.monitor.rest.memory;

public class MemoryPool {

    private String name;
    private long init;
    private long committed;
    private long max;
    private long used;

    public String getName() {
        return name;
    }

    public MemoryPool setName(String name) {
        this.name = name;
        return this;
    }

    public long getInit() {
        return init;
    }

    public MemoryPool setInit(long init) {
        this.init = init;
        return this;
    }

    public long getCommitted() {
        return committed;
    }

    public MemoryPool setCommitted(long committed) {
        this.committed = committed;
        return this;
    }

    public long getMax() {
        return max;
    }

    public MemoryPool setMax(long max) {
        this.max = max;
        return this;
    }

    public long getUsed() {
        return used;
    }

    public MemoryPool setUsed(long used) {
        this.used = used;
        return this;
    }
}
