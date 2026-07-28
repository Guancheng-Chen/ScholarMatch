package com.scholarmatch.frameworks.data_access_object.localMockServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SecureVerificationCodeGeneratorTest {

    @Test
    void testGenerateCodeReturnsSixDigits() {
        final SecureVerificationCodeGenerator generator = new SecureVerificationCodeGenerator();

        for (int i = 0; i < 20; i++) {
            final String code = generator.generateCode();
            assertTrue(code.matches("\\d{6}"), "expected a six-digit code but got: " + code);
        }
    }
}
