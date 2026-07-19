package com.scholarmatch.frameworks.data_access_object;

import java.util.List;
import java.util.function.Supplier;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;
import com.scholarmatch.usecase.exception.DataAccessException;
import com.scholarmatch.usecase.paper_lookup.AuthorCandidateData;

/**
 * Decorator over {@link UserAPIGatewayInterface} that first tries a primary
 * implementation and transparently falls back to a secondary implementation.
 *
 * <p>This prevents a live demo from failing when the Semantic Scholar API is
 * rate-limited or unreachable. Both gateways implement the same use-case port,
 * so callers do not need to know that fallback behaviour exists.
 */
public final class FallbackUserApiGateway implements UserAPIGatewayInterface {

    private final UserAPIGatewayInterface primary;
    private final UserAPIGatewayInterface fallback;

    /**
     * Constructs a fallback user API gateway.
     *
     * @param primary the preferred implementation, which is tried first
     * @param fallback the implementation used when the primary fails
     */
    public FallbackUserApiGateway(
            final UserAPIGatewayInterface primary,
            final UserAPIGatewayInterface fallback) {
        this.primary = primary;
        this.fallback = fallback;
    }

    @Override
    public List<AuthorCandidateData> searchAuthors(final String name) {
        return attempt(
                () -> this.primary.searchAuthors(name),
                () -> this.fallback.searchAuthors(name));
    }

    @Override
    public List<Publication> getAuthorPapers(final String authorId) {
        return attempt(
                () -> this.primary.getAuthorPapers(authorId),
                () -> this.fallback.getAuthorPapers(authorId));
    }

    /**
     * Executes the primary call and retries with the fallback if necessary.
     *
     * <p>If both calls fail, the primary exception is rethrown because it
     * represents the original reason that fallback behaviour was required.
     *
     * @param primaryCall the preferred operation
     * @param fallbackCall the fallback operation
     * @param <T> the type returned by the operation
     * @return the result from the primary or fallback operation
     * @throws DataAccessException if both operations fail
     */
    private <T> T attempt(
            final Supplier<T> primaryCall,
            final Supplier<T> fallbackCall) {
        try {
            return primaryCall.get();
        }
        catch (final DataAccessException primaryError) {
            try {
                return fallbackCall.get();
            }
            catch (final DataAccessException fallbackError) {
                throw primaryError;
            }
        }
    }
}
