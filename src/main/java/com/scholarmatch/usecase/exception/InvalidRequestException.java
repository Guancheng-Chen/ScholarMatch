package com.scholarmatch.usecase.exception;

/**
 * Thrown when a data source rejects a request for a business reason: invalid credentials,
 * an email address that is already registered, or data that fails server-side validation.
 *
 * <p>Distinguished from ExternalServiceException in that the request reached the
 * data source and was understood, but rejected — retrying with the same input will not help.
 */
public class InvalidRequestException extends DataAccessException {

    /**
     * Constructs an InvalidRequestException with no underlying cause.
     *
     * @param message a human-readable description of why the request was rejected
     */
    public InvalidRequestException(final String message) {
        super(message);
    }

    /**
     * Constructs an InvalidRequestException wrapping a lower-level cause.
     *
     * @param message a human-readable description of why the request was rejected
     * @param cause   the low-level exception being wrapped
     */
    public InvalidRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
