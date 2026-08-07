package com.scholarmatch.frameworks.data_access_object.local_mock_server;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryEmailVerificationChallengeRepositoryTest {

    @Test
    void testNullEmailLookupNormalizesToEmptyStringAndFindsNothing() {
        final InMemoryEmailVerificationChallengeRepository repository =
                new InMemoryEmailVerificationChallengeRepository();

        final Optional<EmailVerificationChallenge> found = repository.findByEmail(null);

        assertTrue(found.isEmpty());
    }

    @Test
    void testSaveAndFindByEmailNormalizesCasingAndWhitespace() {
        final InMemoryEmailVerificationChallengeRepository repository =
                new InMemoryEmailVerificationChallengeRepository();
        final EmailVerificationChallenge challenge = new EmailVerificationChallenge(
                " Ada@Example.com ", "123456", Instant.now().plus(Duration.ofMinutes(10)));

        repository.save(challenge);

        assertEquals(challenge, repository.findByEmail("ada@example.com").orElseThrow());

        repository.deleteByEmail("ADA@EXAMPLE.COM");

        assertTrue(repository.findByEmail("ada@example.com").isEmpty());
    }
}
