package com.scholarmatch.frameworks.data_access_object;

import com.scholarmatch.usecase.data_access_interface.VerificationCodeGeneratorInterface;

import java.security.SecureRandom;
import java.util.Locale;

/**
 * Cryptographically strong six-digit verification code generator.
 */
public final class SecureVerificationCodeGenerator implements VerificationCodeGeneratorInterface {

    private static final int CODE_RANGE = 1_000_000;
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateCode() {
        return String.format(Locale.ROOT, "%06d", this.random.nextInt(CODE_RANGE));
    }
}
