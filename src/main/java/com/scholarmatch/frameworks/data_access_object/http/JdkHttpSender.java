package com.scholarmatch.frameworks.data_access_object.http;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Default {@link HttpSender} backed by the JDK's {@link HttpClient}.
 */
public final class JdkHttpSender implements HttpSender {

    private final HttpClient httpClient;

    public JdkHttpSender(final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public HttpSenderResponse send(final HttpRequest request) throws IOException, InterruptedException {
        final HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return new HttpSenderResponse(response.statusCode(), response.body());
    }
}
