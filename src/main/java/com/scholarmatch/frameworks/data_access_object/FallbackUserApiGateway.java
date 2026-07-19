package com.scholarmatch.frameworks.data_access_object;

import java.util.List;

import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.data_access_interface.AuthorCandidateDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UserAPIGatewayInterface;

/**
 * Gateway decorator that supports a primary and fallback implementation.
 */
public final class FallbackUserApiGateway implements UserAPIGatewayInterface {

    private final UserAPIGatewayInterface primary;
    private final UserAPIGatewayInterface fallback;

    /**
     * Constructs a fallback user API gateway.
     *
     * @param primary the preferred gateway implementation
     * @param fallback the gateway used if the primary fails
     */
    public FallbackUserApiGateway(
            final UserAPIGatewayInterface primary,
            final UserAPIGatewayInterface fallback) {
        this.primary = primary;
        this.fallback = fallback;
    }

    @Override
    public List<AuthorCandidateDataAccessInterface> searchAuthors(
            final String name) {
        return this.primary.searchAuthors(name);
    }

    @Override
    public List<Publication> fetchPapersByAuthorId(final String authorId) {
        return this.primary.fetchPapersByAuthorId(authorId);
    }
}
