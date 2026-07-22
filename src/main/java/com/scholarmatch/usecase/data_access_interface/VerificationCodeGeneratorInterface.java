package com.scholarmatch.usecase.data_access_interface;

/**
 * Boundary for generating registration verification codes.
 */
public interface VerificationCodeGeneratorInterface {

    String generateCode();
}
