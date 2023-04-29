package com.app.monitor.processor;

import com.app.monitor.logic.RunnerLoop;
import org.springframework.boot.ExitCodeEvent;

public class AppProcessor {

    private final RunnerLoop runnerLoop;
    private final Thread runnerThread;

    public AppProcessor(RunnerLoop runnerLoop, Thread runnerThread) {
        this.runnerLoop = runnerLoop;
        this.runnerThread = runnerThread;
    }

    public void start() {
        runnerLoop.init();
        runnerThread.start();
    }

    public void shutdown(ExitCodeEvent event) {
        runnerLoop.stop(event);
    }
}
