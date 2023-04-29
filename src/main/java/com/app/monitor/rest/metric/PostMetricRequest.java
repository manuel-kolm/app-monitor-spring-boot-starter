package com.app.monitor.rest.metric;

import java.util.ArrayList;
import java.util.List;

public class PostMetricRequest {

    private List<Metric> metrics;

    public List<Metric> getMetrics() {
        if (metrics == null) {
            metrics = new ArrayList<>();
        }
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }
}
