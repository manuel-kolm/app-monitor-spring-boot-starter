package com.app.monitor.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class HttpSender {

    private final Logger LOG = LoggerFactory.getLogger(HttpSender.class);
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final List<String> messageQueue = new ArrayList<>();
    private final ObjectMapper objectMapper;

    private String processId;

    public HttpSender(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T send(String path, Object object, Class responseClass) {
        try {
            String message = objectMapper.writeValueAsString(object);

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11443" + path))
                    .POST(HttpRequest.BodyPublishers.ofString(message))
                    .setHeader("Content-Type", "application/json");

            if (processId != null) {
                builder.setHeader("x-process-id", processId);
            }

            HttpRequest request = builder.build();

            HttpResponse<String> data = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (data.statusCode() == HttpStatus.NO_CONTENT.value()) {
                return null;
            }

            return (T) objectMapper.readValue(data.body(), responseClass);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
