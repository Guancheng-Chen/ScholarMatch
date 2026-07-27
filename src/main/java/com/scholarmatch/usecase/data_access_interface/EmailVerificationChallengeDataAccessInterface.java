package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.EmailVerificationChallenge;

import java.util.Optional;

/**
 * Persistence boundary for pending email verification challenges.
 */
public interface EmailVerificationChallengeDataAccessInterface {

    void save(EmailVerificationChallenge challenge);

    Optional<EmailVerificationChallenge> findByEmail(String email);

    void deleteByEmail(String email);
}
