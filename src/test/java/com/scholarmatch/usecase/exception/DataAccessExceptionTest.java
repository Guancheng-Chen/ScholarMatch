package com.scholarmatch.usecase.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class DataAccessExceptionTest {

    @Test
    void testInvalidRequestExceptionWrapsCause() {
        final Throwable cause = new IllegalArgumentException("invalid");
        final InvalidRequestException exception =
                new InvalidRequestException("Request rejected", cause);

        assertEquals("Request rejected", exception.getMessage());
        assertSame(cause, exception.getCause());
    }

    @Test
    void testResourceNotFoundExceptionWrapsCause() {
        final Throwable cause = new IllegalStateException("missing");
        final ResourceNotFoundException exception =
                new ResourceNotFoundException("Resource missing", cause);

        assertEquals("Resource missing", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
