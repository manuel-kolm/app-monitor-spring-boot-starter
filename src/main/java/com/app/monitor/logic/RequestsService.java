package com.app.monitor.logic;

import com.app.monitor.handler.HttpRequestWrapper;
import com.app.monitor.rest.networking.Request;

import java.util.ArrayList;
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
                ).toList();
        requests.clear();

        httpSender.send("/traces/requests", requestsCopy, Void.class);
    }
}
