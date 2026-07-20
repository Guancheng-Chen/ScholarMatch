package com.scholarmatch.frameworks.data_access_object.http;

import java.io.IOException;
import java.net.http.HttpRequest;

/**
 * Boundary for sending a blocking HTTP request and reading back a string body.
 * Gateways depend on this instead of {@link java.net.http.HttpClient} directly, and
 * the response is our own HttpSenderResponse rather than a JDK type, so tests
 * can mock and construct plain, unrestricted classes instead of the JDK's HTTP types.
 */
public interface HttpSender {
    HttpSenderResponse send(HttpRequest request) throws IOException, InterruptedException;
}
