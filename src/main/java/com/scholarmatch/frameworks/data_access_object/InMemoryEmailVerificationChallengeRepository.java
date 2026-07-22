package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.entity.EmailVerificationChallenge;
import com.scholarmatch.usecase.data_access_interface.EmailVerificationChallengeDataAccessInterface;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Process-local storage for pending registration verification challenges.
 */
public final class InMemoryEmailVerificationChallengeRepository
        implements EmailVerificationChallengeDataAccessInterface {

    private final Map<String, EmailVerificationChallenge> challenges = new ConcurrentHashMap<>();

    @Override
    public void save(final EmailVerificationChallenge challenge) {
        this.challenges.put(normalize(challenge.getEmail()), challenge);
    }

    @Override
    public Optional<EmailVerificationChallenge> findByEmail(final String email) {
        return Optional.ofNullable(this.challenges.get(normalize(email)));
    }

    @Override
    public void deleteByEmail(final String email) {
        this.challenges.remove(normalize(email));
    }

    private String normalize(final String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}
