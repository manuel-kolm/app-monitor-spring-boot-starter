package com.app.monitor.config;

import com.app.monitor.analyser.GarbageCollectionAnalyser;
import com.app.monitor.analyser.JvmAnalyser;
import com.app.monitor.handler.AsyncRequestHandler;
import com.app.monitor.handler.RequestsHandler;
import com.app.monitor.logic.HttpSender;
import com.app.monitor.logic.MetricsHandler;
import com.app.monitor.logic.RequestsService;
import com.app.monitor.logic.RunnerLoop;
import com.app.monitor.processor.AppProcessor;
import com.app.monitor.processor.ApplicationShutdownListener;
import com.app.monitor.processor.ApplicationStartupListener;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties({AppMonitorConfigProperties.class})
public class AppMonitorAutoConfiguration {

    @Bean
    HttpSender httpSender(ObjectMapper objectMapper) {
        return new HttpSender(objectMapper);
    }

    @Bean
    JvmAnalyser jvmAnalyser() {
        return new JvmAnalyser();
    }

    @Bean
    MetricsHandler metricsHandler(JvmAnalyser jvmAnalyser, GarbageCollectionAnalyser garbageCollectionAnalyser, HttpSender httpSender) {
        return new MetricsHandler(jvmAnalyser, garbageCollectionAnalyser, httpSender);
    }

    @Bean
    ApplicationStartupListener listener(ApplicationContext context, AppProcessor processor) {
        return new ApplicationStartupListener(context, processor);
    }

    @Bean
    ApplicationShutdownListener applicationShutdownListener(ApplicationContext context, AppProcessor processor) {
        return new ApplicationShutdownListener(context, processor);
    }

    @Bean
    RunnerLoop runnerLoop(MetricsHandler metricsHandler, RequestsService requestsService, JvmAnalyser jvmAnalyser, HttpSender httpSender, AppMonitorConfigProperties appMonitorConfigProperties) {
        return new RunnerLoop(metricsHandler, requestsService, jvmAnalyser, httpSender, appMonitorConfigProperties);
    }

    @Bean
    @Qualifier("runnerThread")
    @ConditionalOnMissingBean
    Thread runnerThread(RunnerLoop runnerLoop) {
        return new Thread(runnerLoop);
    }

    @Bean
    AppProcessor processor(RunnerLoop runnerLoop, @Qualifier("runnerThread") Thread runnerThread) {
        return new AppProcessor(runnerLoop, runnerThread);
    }

    @Bean
    ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module);
    }

    @Bean
    GarbageCollectionAnalyser garbageCollectionAnalyser() {
        return new GarbageCollectionAnalyser();
    }

    @Bean
    RequestsService requestsService(HttpSender httpSender) {
        return new RequestsService(httpSender);
    }

    @Bean
    RequestsHandler requestHandler(RequestsService requestTraceService) {
        return new RequestsHandler(requestTraceService);
    }

    @Bean
    AsyncRequestHandler asyncRequestHandler() {
        return new AsyncRequestHandler();
    }
}
