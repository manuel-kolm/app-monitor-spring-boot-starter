package com.app.monitor.config;

import com.app.monitor.handler.AsyncRequestHandler;
import com.app.monitor.handler.RequestsHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private final RequestsHandler requestHandler;
    private final AsyncRequestHandler asyncRequestHandler;

    public WebMvcAutoConfiguration(RequestsHandler requestHandler, AsyncRequestHandler asyncRequestHandler) {
        this.requestHandler = requestHandler;
        this.asyncRequestHandler = asyncRequestHandler;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHandler);
        registry.addInterceptor(asyncRequestHandler);
    }
}
