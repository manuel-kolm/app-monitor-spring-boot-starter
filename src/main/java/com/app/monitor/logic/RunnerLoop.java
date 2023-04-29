package com.app.monitor.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnerLoop implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(RunnerLoop.class);
    private final HttpSender httpSender;
    private final MetricsHandler metricsHandler;
    private final RequestsService requestsService;

    public RunnerLoop(HttpSender httpSender, MetricsHandler metricsHandler, RequestsService requestsService) {
        this.httpSender = httpSender;
        this.metricsHandler = metricsHandler;
        this.requestsService = requestsService;
    }

    @Override
    public void run() {
        try {
            while (true) {
                requestsService.sendAll();
                metricsHandler.sendAll();

                Thread.sleep(60000 - System.currentTimeMillis() % 60000);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }
}
