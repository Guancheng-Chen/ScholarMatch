package com.scholarmatch.usecase.register;

/**
 * Validated account data passed to the registration gateway. The email verification code is
 * forwarded as-is; the server is the one that checks it and classifies the email domain.
 */
public final class RegisterAccountData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final String verificationCode;

    /**
     * Constructs registration data ready to send to the server.
     *
     * @param firstName        given name
     * @param lastName         family name
     * @param email            email address
     * @param password         plain-text password
     * @param verificationCode the code the user typed in, for the server to check
     */
    public RegisterAccountData(
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final String verificationCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
    }

    /**
     * @return the given name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the family name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return the plain-text password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @return the verification code the user typed in
     */
    public String getVerificationCode() {
        return this.verificationCode;
    }
}
