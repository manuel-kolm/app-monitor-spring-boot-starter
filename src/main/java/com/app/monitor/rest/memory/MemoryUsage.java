package com.app.monitor.rest.memory;

public class MemoryUsage {

    private long init;
    private long committed;
    private long max;
    private long used;

    public long getInit() {
        return init;
    }

    public MemoryUsage setInit(long init) {
        this.init = init;
        return this;
    }

    public long getCommitted() {
        return committed;
    }

    public MemoryUsage setCommitted(long committed) {
        this.committed = committed;
        return this;
    }

    public long getMax() {
        return max;
    }

    public MemoryUsage setMax(long max) {
        this.max = max;
        return this;
    }

    public long getUsed() {
        return used;
    }

    public MemoryUsage setUsed(long used) {
        this.used = used;
        return this;
    }
}
