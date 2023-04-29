package com.app.monitor.logic;

import com.app.monitor.handler.HttpRequestWrapper;
import com.app.monitor.rest.networking.Request;
import com.app.monitor.rest.system.ExceptionInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RequestsService {

    private final List<HttpRequestWrapper> requests = Collections.synchronizedList(new ArrayList<>());
    private final HttpSender httpSender;

    public RequestsService(HttpSender httpSender) {
        this.httpSender = httpSender;
    }

    public void enqueue(HttpRequestWrapper requestWrapper) {
        requests.add(requestWrapper);
    }

    public void sendAll() {
        if (requests.size() < 1) {
            return;
        }

        List<Request> requestsCopy = requests.stream()
                .map(r -> new Request().setPath(r.getPath())
                        .setId(UUID.randomUUID().toString())
                        .setMethod(r.getMethod())
                        .setStatusCode(r.getStatusCode())
                        .setStarted(r.getStarted())
                        .setDuration(r.getDuration())
                        .setException(translateException(r.getException()))
                ).toList();
        requests.clear();

        httpSender.send("/traces/requests", requestsCopy, Void.class);
    }

    private ExceptionInfo translateException(Exception exception) {
        return new ExceptionInfo()
                .setClassId(exception.getClass().getClass().getName())
                .setMessage(exception.getMessage())
                .setStackTrace(Arrays.stream(exception.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toList()
                );
    }
}
