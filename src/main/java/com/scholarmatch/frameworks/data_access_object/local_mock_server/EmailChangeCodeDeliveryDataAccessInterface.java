package com.scholarmatch.frameworks.data_access_object.local_mock_server;

/**
 * Delivery boundary for an email-change verification code.
 */
public interface EmailChangeCodeDeliveryDataAccessInterface {

    void sendCode(String email, String code);
}
