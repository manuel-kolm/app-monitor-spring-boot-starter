package com.app.monitor.processor;

import com.app.monitor.annotation.EnableAppMonitor;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.Optional;

public class ApplicationShutdownListener implements ApplicationListener<ExitCodeEvent> {

    private ApplicationContext context;
    private AppProcessor processor;

    public ApplicationShutdownListener(ApplicationContext context, AppProcessor processor) {
        this.context = context;
        this.processor = processor;
    }

    @Override
    public void onApplicationEvent(ExitCodeEvent event) {
        Optional<EnableAppMonitor> annotation = context.getBeansWithAnnotation(EnableAppMonitor.class)
                .keySet().stream()
                .map(key -> context.findAnnotationOnBean(key, EnableAppMonitor.class))
                .findFirst();

        annotation.ifPresent(enableDatadog -> processor.shutdown(event));
    }
}
