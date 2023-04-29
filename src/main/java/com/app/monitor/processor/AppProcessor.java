package com.app.monitor.processor;

import com.app.monitor.analyser.JvmAnalyser;
import com.app.monitor.logic.HttpSender;
import com.app.monitor.rest.host.CreateProcessRequest;
import com.app.monitor.rest.host.CreateProcessResponse;
import com.app.monitor.rest.host.DeleteProcessRequest;
import org.springframework.boot.ExitCodeEvent;

import java.time.ZonedDateTime;

public class AppProcessor {

    private final HttpSender httpSender;
    private final JvmAnalyser jvmAnalyser;
    private final Thread runnerThread;

    public AppProcessor(HttpSender httpSender, JvmAnalyser jvmAnalyser, Thread runnerThread) {
        this.httpSender = httpSender;
        this.jvmAnalyser = jvmAnalyser;
        this.runnerThread = runnerThread;
    }

    public void start() {
        CreateProcessRequest startupMessage = new CreateProcessRequest()
                .setOperatingSystem(jvmAnalyser.getOperatingSystem())
                .setCreated(ZonedDateTime.now());

        CreateProcessResponse response = httpSender.send("/processes", startupMessage, CreateProcessResponse.class);
        httpSender.setProcessId(response.getId());

        runnerThread.start();
    }

    public void shutdown(ExitCodeEvent event) {
        DeleteProcessRequest shutdownMessage = new DeleteProcessRequest()
                .setExitCode(event.getExitCode());

        httpSender.send("/processes", shutdownMessage, Void.class);
    }
}
