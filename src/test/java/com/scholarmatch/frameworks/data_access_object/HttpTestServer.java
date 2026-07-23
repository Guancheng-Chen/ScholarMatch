package com.scholarmatch.frameworks.data_access_object;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Embedded loopback HTTP server for testing ServerHttpClient and the gateways built on it —
 * shared across their test classes instead of each one duplicating the same server setup, the
 * way a single ServerRepositoryTest used to before it was split per gateway.
 */
final class HttpTestServer {

    private final AtomicInteger statusToReturn = new AtomicInteger(200);
    private final AtomicReference<String> bodyToReturn = new AtomicReference<>("{}");
    private final AtomicReference<String> lastRequestBody = new AtomicReference<>();
    private final AtomicReference<String> lastAuthHeader = new AtomicReference<>();
    private final AtomicReference<String> lastMethod = new AtomicReference<>();
    private final AtomicReference<String> lastPath = new AtomicReference<>();

    private final HttpServer server;

    HttpTestServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        this.server.createContext("/", exchange -> {
            this.lastMethod.set(exchange.getRequestMethod());
            this.lastPath.set(exchange.getRequestURI().toString());
            this.lastAuthHeader.set(exchange.getRequestHeaders().getFirst("Authorization"));
            this.lastRequestBody.set(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            final String responseBody = this.bodyToReturn.get();
            if (responseBody == null) {
                exchange.sendResponseHeaders(this.statusToReturn.get(), -1);
            } else {
                final byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(this.statusToReturn.get(), bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        });
        this.server.start();
    }

    AtomicInteger statusToReturn() {
        return this.statusToReturn;
    }

    AtomicReference<String> bodyToReturn() {
        return this.bodyToReturn;
    }

    AtomicReference<String> lastRequestBody() {
        return this.lastRequestBody;
    }

    AtomicReference<String> lastAuthHeader() {
        return this.lastAuthHeader;
    }

    AtomicReference<String> lastMethod() {
        return this.lastMethod;
    }

    AtomicReference<String> lastPath() {
        return this.lastPath;
    }

    String baseUrl() {
        return "http://127.0.0.1:" + this.server.getAddress().getPort();
    }

    void stop() {
        this.server.stop(0);
    }
}
