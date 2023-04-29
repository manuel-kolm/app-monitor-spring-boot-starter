package com.app.monitor.handler;

import com.app.monitor.logic.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.ZonedDateTime;

public class RequestsHandler implements HandlerInterceptor {

    private final RequestsService requestsService;

    public RequestsHandler(RequestsService requestsService) {
        this.requestsService = requestsService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("request-start-time", System.currentTimeMillis());
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper()
                .setInternalId(request.getRequestId())
                .setPath(request.getRequestURI())
                .setStatusCode(response.getStatus())
                .setMethod(request.getMethod())
                .setStarted(ZonedDateTime.now())
                .setDuration(System.currentTimeMillis() - (Long) request.getAttribute("request-start-time"));

        requestsService.enqueue(requestWrapper);
    }
}
