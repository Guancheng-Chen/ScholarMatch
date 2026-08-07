package com.scholarmatch.frameworks.data_access_object.local_mock_server;

/**
 * Boundary for generating registration verification codes.
 */
public interface VerificationCodeGeneratorInterface {

    String generateCode();
}
