package com.app.monitor.logic;

import com.app.monitor.analyser.GarbageCollectionAnalyser;
import com.app.monitor.analyser.JvmAnalyser;
import com.app.monitor.rest.metric.Metric;
import com.app.monitor.rest.metric.MetricData;

import java.time.ZonedDateTime;

public class MetricsHandler {

    private final JvmAnalyser jvmAnalyser;
    private final GarbageCollectionAnalyser garbageCollectionAnalyser;
    private final HttpSender httpSender;

    public MetricsHandler(JvmAnalyser jvmAnalyser, GarbageCollectionAnalyser garbageCollectionAnalyser, HttpSender httpSender) {
        this.jvmAnalyser = jvmAnalyser;
        this.garbageCollectionAnalyser = garbageCollectionAnalyser;
        this.httpSender = httpSender;
    }

    public void sendAll() {
        Metric metric = new Metric();
        metric.getData().add(getCurrentMetricData());
        httpSender.send("/metrics", metric, Void.class);
    }

    private MetricData getCurrentMetricData() {
        return new MetricData()
                .setCreated(ZonedDateTime.now())
                .setCpu(jvmAnalyser.getCpu())
                .setMemoryPools(jvmAnalyser.getCurrentMemoryPools())
                .setHeap(jvmAnalyser.getHeapMemoryUsage())
                .setNonHeap(jvmAnalyser.getNonHeapMemoryUsage())
                .setGarbageCollections(garbageCollectionAnalyser.getCollections());
    }
}
