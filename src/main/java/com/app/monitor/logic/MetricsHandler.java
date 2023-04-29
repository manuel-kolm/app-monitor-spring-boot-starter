package com.app.monitor.logic;

import com.app.monitor.analyser.GarbageCollectionAnalyser;
import com.app.monitor.analyser.JvmAnalyser;
import com.app.monitor.rest.metric.Metric;
import com.app.monitor.rest.metric.PostMetricRequest;
import org.springframework.stereotype.Service;

@Service
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
        PostMetricRequest request = new PostMetricRequest();
        request.getMetrics().add(getCurrentMetric());
        httpSender.send("/metrics", request, Void.class);
    }

    private Metric getCurrentMetric() {
        return new Metric()
                .setCpu(jvmAnalyser.getCpu())
                .setMemoryPools(jvmAnalyser.getCurrentMemoryPools())
                .setHeap(jvmAnalyser.getHeapMemoryUsage())
                .setNonHeap(jvmAnalyser.getNonHeapMemoryUsage())
                .setGarbageCollections(garbageCollectionAnalyser.getCollections());
    }
}
