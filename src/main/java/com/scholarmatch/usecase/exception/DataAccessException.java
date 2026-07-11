package com.scholarmatch.usecase.exception;

/**
 * Base type for failures that occur while a data access port
 * (usecase.data_access_interface) talks to its underlying data source — a remote
 * server, a third-party API, or an in-memory offline store.
 *
 * <p>Frameworks-layer implementations (HTTP gateways, offline repositories) must wrap any
 * low-level failure (I/O errors, JSON parsing errors, non-2xx responses) in one of this
 * class's subclasses rather than letting it escape as-is or as a bare RuntimeException.
 * This lets interactors depend on a single well-known exception type defined by the usecase
 * layer, instead of depending on implementation details of whichever transport a given port
 * happens to use.
 *
 * <p>The constructors are protected rather than private/public so that only
 * subclasses — not arbitrary callers — can create instances, without tripping the project's
 * "abstract classes must be named Abstract*" checkstyle rule.
 */
public class DataAccessException extends RuntimeException {

    /**
     * Constructs a DataAccessException with no underlying cause.
     *
     * @param message a human-readable description of the failure
     */
    protected DataAccessException(final String message) {
        super(message);
    }

    /**
     * Constructs a DataAccessException wrapping a lower-level cause.
     *
     * @param message a human-readable description of the failure
     * @param cause   the low-level exception being wrapped (e.g. an I/O or parsing error)
     */
    protected DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
