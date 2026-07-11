package com.scholarmatch.usecase.exception;

/**
 * Thrown when the underlying transport or infrastructure behind a data access port fails:
 * the network is unreachable, a request times out, a response cannot be parsed, or the
 * remote server reports an unexpected (5xx) error.
 *
 * <p>Distinguished from InvalidRequestException in that the failure is not about
 * the request's content — retrying later (or against a healthy server) may succeed.
 */
public class ExternalServiceException extends DataAccessException {

    /**
     * Constructs an ExternalServiceException with no underlying cause.
     *
     * @param message a human-readable description of the failure
     */
    public ExternalServiceException(final String message) {
        super(message);
    }

    /**
     * Constructs an ExternalServiceException wrapping a lower-level cause.
     *
     * @param message a human-readable description of the failure
     * @param cause   the low-level exception being wrapped (e.g. an I/O or parsing error)
     */
    public ExternalServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
