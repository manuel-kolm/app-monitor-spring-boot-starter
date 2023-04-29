package com.app.monitor.config;

import com.app.monitor.handler.AsyncRequestHandler;
import com.app.monitor.handler.RequestsHandler;
import com.app.monitor.logic.GCListener;
import com.app.monitor.logic.HttpSender;
import com.app.monitor.logic.JvmAnalyser;
import com.app.monitor.logic.MetricsHandler;
import com.app.monitor.logic.RequestsService;
import com.app.monitor.logic.RunnerLoop;
import com.app.monitor.processor.AppProcessor;
import com.app.monitor.processor.ApplicationStartupListener;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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
    MetricsHandler metricsHandler(JvmAnalyser jvmAnalyser, HttpSender httpSender) {
        return new MetricsHandler(jvmAnalyser, httpSender);
    }

    @Bean
    ApplicationStartupListener listener(ApplicationContext context, AppProcessor processor) {
        return new ApplicationStartupListener(context, processor);
    }

    @Bean
    @Qualifier("runnerThread")
    @ConditionalOnMissingBean
    Thread runnerThread(MetricsHandler metricsHandler, HttpSender httpSender, RequestsService requestsService) {
        return new Thread(new RunnerLoop(httpSender, metricsHandler, requestsService));
    }

    @Bean
    AppProcessor processor(HttpSender httpSender, JvmAnalyser jvmAnalyser, @Qualifier("runnerThread") Thread runnerThread) {
        return new AppProcessor(httpSender, jvmAnalyser, runnerThread);
    }

    @Bean
    ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module);
    }

    @Bean
    GCListener gcListener() {
        return new GCListener();
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
