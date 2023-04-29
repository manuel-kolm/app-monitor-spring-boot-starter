package com.app.monitor.logic;

import com.app.monitor.rest.metric.PostMetricRequest;
import org.springframework.stereotype.Service;

@Service
public class MetricsHandler {

    private final JvmAnalyser jvmAnalyser;
    private final HttpSender httpSender;

    public MetricsHandler(JvmAnalyser jvmAnalyser, HttpSender httpSender) {
        this.jvmAnalyser = jvmAnalyser;
        this.httpSender = httpSender;
    }

    public void sendAll() {
        PostMetricRequest msg = new PostMetricRequest();
        msg.getMetrics().add(jvmAnalyser.getMetric());
        httpSender.send("/metrics", msg, Void.class);
    }
}
