package com.scholarmatch.usecase.data_access_interface;

/**
 * Delivery boundary for an email-change verification code.
 */
public interface EmailChangeCodeDeliveryDataAccessInterface {

    void sendCode(String email, String code);
}
