package com.app.monitor.rest.metric;

import com.app.monitor.rest.memory.GarbageCollection;
import com.app.monitor.rest.memory.MemoryPool;
import com.app.monitor.rest.memory.MemoryUsage;
import com.app.monitor.rest.system.Cpu;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Metric {

    private ZonedDateTime created;
    private Cpu cpu;
    private List<MemoryPool> memoryPools;
    private MemoryUsage heap;
    private MemoryUsage nonHeap;
    private List<GarbageCollection> garbageCollections;

    public ZonedDateTime getCreated() {
        return created;
    }

    public Metric setCreated(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public Metric setCpu(Cpu cpu) {
        this.cpu = cpu;
        return this;
    }

    public List<MemoryPool> getMemoryPools() {
        if (memoryPools == null) {
            memoryPools = new ArrayList<>();
        }
        return memoryPools;
    }

    public Metric setMemoryPools(List<MemoryPool> memoryPools) {
        this.memoryPools = memoryPools;
        return this;
    }

    public MemoryUsage getHeap() {
        return heap;
    }

    public Metric setHeap(MemoryUsage heap) {
        this.heap = heap;
        return this;
    }

    public MemoryUsage getNonHeap() {
        return nonHeap;
    }

    public Metric setNonHeap(MemoryUsage nonHeap) {
        this.nonHeap = nonHeap;
        return this;
    }

    public List<GarbageCollection> getGarbageCollections() {
        if (garbageCollections == null) {
            garbageCollections = new ArrayList<>();
        }
        return garbageCollections;
    }

    public Metric setGarbageCollections(List<GarbageCollection> garbageCollections) {
        this.garbageCollections = garbageCollections;
        return this;
    }
}
