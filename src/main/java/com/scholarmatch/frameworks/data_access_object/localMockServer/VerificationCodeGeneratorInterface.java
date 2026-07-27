package com.scholarmatch.frameworks.data_access_object.localMockServer;

/**
 * Boundary for generating registration verification codes.
 */
public interface VerificationCodeGeneratorInterface {

    String generateCode();
}
