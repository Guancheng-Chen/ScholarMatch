package com.scholarmatch.usecase.exception;

/**
 * Thrown when the resource a data access port was asked for does not exist: an HTTP 404
 * response, or an ID that has no corresponding entry in an offline dataset.
 */
public class ResourceNotFoundException extends DataAccessException {

    /**
     * Constructs a ResourceNotFoundException with no underlying cause.
     *
     * @param message a human-readable description of which resource was missing
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }

    /**
     * Constructs a ResourceNotFoundException wrapping a lower-level cause.
     *
     * @param message a human-readable description of which resource was missing
     * @param cause   the low-level exception being wrapped
     */
    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
