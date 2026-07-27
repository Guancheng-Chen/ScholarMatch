package com.scholarmatch.frameworks.data_access_object.localMockServer;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Process-local storage for pending email verification challenges.
 */
public final class InMemoryEmailVerificationChallengeRepository {

    private final Map<String, EmailVerificationChallenge> challenges =
            new ConcurrentHashMap<>();

    public void save(final EmailVerificationChallenge challenge) {
        this.challenges.put(normalize(challenge.getEmail()), challenge);
    }

    public Optional<EmailVerificationChallenge> findByEmail(final String email) {
        return Optional.ofNullable(this.challenges.get(normalize(email)));
    }

    public void deleteByEmail(final String email) {
        this.challenges.remove(normalize(email));
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
