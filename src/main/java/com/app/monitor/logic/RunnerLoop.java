package com.app.monitor.logic;

import com.app.monitor.analyser.JvmAnalyser;
import com.app.monitor.config.AppMonitorConfigProperties;
import org.springframework.boot.ExitCodeEvent;

import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunnerLoop implements Runnable {

    private static final Logger LOG = Logger.getLogger(RunnerLoop.class.getName());
    private final MetricsHandler metricsHandler;
    private final RequestsService requestsService;
    private final JvmAnalyser jvmAnalyser;
    private final HttpSender httpSender;
    private final AppMonitorConfigProperties appMonitorConfigProperties;

    public RunnerLoop(MetricsHandler metricsHandler, RequestsService requestsService, JvmAnalyser jvmAnalyser, HttpSender httpSender, AppMonitorConfigProperties appMonitorConfigProperties) {
        this.metricsHandler = metricsHandler;
        this.requestsService = requestsService;
        this.jvmAnalyser = jvmAnalyser;
        this.httpSender = httpSender;
        this.appMonitorConfigProperties = appMonitorConfigProperties;
    }

    public void init() {
        com.app.monitor.rest.host.Process processRequest = new com.app.monitor.rest.host.Process()
                .setName(appMonitorConfigProperties.getName())
                .setOperatingSystem(jvmAnalyser.getOperatingSystem())
                .setCreated(ZonedDateTime.now());

        com.app.monitor.rest.host.Process response = httpSender.send("/processes", processRequest, com.app.monitor.rest.host.Process.class);
        httpSender.setProcessId(response.getId());
    }

    public void stop(ExitCodeEvent event) {
/*        DeleteProcessRequest shutdownMessage = new DeleteProcessRequest()
                .setExitCode(event.getExitCode());

        httpSender.send("/processes", shutdownMessage, Void.class);*/
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
            LOG.log(Level.SEVERE, e.toString());
        }
    }
}
