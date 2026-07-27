package com.scholarmatch.frameworks.data_access_object.localMockServer;

/**
 * Delivery boundary for an email-change verification code.
 */
public interface EmailChangeCodeDeliveryDataAccessInterface {

    void sendCode(String email, String code);
}
