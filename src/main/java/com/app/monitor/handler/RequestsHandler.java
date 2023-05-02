package com.app.monitor.handler;

import com.app.monitor.config.AppMonitorConfigProperties;
import com.app.monitor.logic.RequestsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.ZonedDateTime;

public class RequestsHandler implements HandlerInterceptor {

    private static final String REQUEST_START_TIME = "request-start-time";
    private final RequestsService requestsService;
    private final AppMonitorConfigProperties appMonitorConfigProperties;

    public RequestsHandler(RequestsService requestsService, AppMonitorConfigProperties appMonitorConfigProperties) {
        this.requestsService = requestsService;
        this.appMonitorConfigProperties = appMonitorConfigProperties;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception exception) throws Exception {

        if (appMonitorConfigProperties.isTrackLocalRequests() && "127.0.0.1".equals(request.getRemoteAddr())) {
            return;
        }

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper()
                .setInternalId(request.getRequestId())
                .setPath(request.getRequestURI())
                .setStatusCode(response.getStatus())
                .setMethod(request.getMethod())
                .setStarted(ZonedDateTime.now())
                .setDuration(System.currentTimeMillis() - (Long) request.getAttribute(REQUEST_START_TIME))
                .setException(exception);

        requestsService.enqueue(requestWrapper);
    }
}
