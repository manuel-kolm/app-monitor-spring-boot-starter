package com.app.monitor.processor;

import com.app.monitor.annotation.EnableAppMonitor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Optional;

public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext context;
    private AppProcessor processor;

    public ApplicationStartupListener(ApplicationContext context, AppProcessor processor) {
        this.context = context;
        this.processor = processor;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Optional<EnableAppMonitor> annotation = context.getBeansWithAnnotation(EnableAppMonitor.class)
                .keySet().stream()
                .map(key -> context.findAnnotationOnBean(key, EnableAppMonitor.class))
                .findFirst();

        annotation.ifPresent(enableDatadog -> processor.start());
    }
}