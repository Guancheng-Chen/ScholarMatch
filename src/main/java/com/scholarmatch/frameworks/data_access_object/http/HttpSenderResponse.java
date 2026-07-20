package com.scholarmatch.frameworks.data_access_object.http;

/**
 * Minimal HTTP response shape used by {@link HttpSender}. Kept independent of
 * {@link java.net.http.HttpResponse} so callers never need to mock a JDK type.
 *
 * @param statusCode the HTTP status code of the response
 * @param body the response body as a string
 */
public record HttpSenderResponse(int statusCode, String body) {
}
