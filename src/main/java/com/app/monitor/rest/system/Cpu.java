package com.app.monitor.rest.system;

public class Cpu {

    private int threadCount;
    private int peakThreadCount;
    private long totalStartedThreadCount;
    private int daemonThreadCount;
    private double processCpuLoad;

    public int getThreadCount() {
        return threadCount;
    }

    public Cpu setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    public Cpu setPeakThreadCount(int peakThreadCount) {
        this.peakThreadCount = peakThreadCount;
        return this;
    }

    public long getTotalStartedThreadCount() {
        return totalStartedThreadCount;
    }

    public Cpu setTotalStartedThreadCount(long totalStartedThreadCount) {
        this.totalStartedThreadCount = totalStartedThreadCount;
        return this;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    public Cpu setDaemonThreadCount(int daemonThreadCount) {
        this.daemonThreadCount = daemonThreadCount;
        return this;
    }

    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    public Cpu setProcessCpuLoad(double processCpuLoad) {
        this.processCpuLoad = processCpuLoad;
        return this;
    }
}
