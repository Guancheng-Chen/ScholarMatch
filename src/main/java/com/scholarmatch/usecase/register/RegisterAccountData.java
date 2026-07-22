package com.scholarmatch.usecase.register;

import com.scholarmatch.entity.EmailAccountType;

/**
 * Validated and classified account data passed to the registration gateway.
 */
public final class RegisterAccountData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final EmailAccountType emailAccountType;

    /**
     * Constructs classified registration data.
     *
     * @param firstName       given name
     * @param lastName        family name
     * @param email           email address
     * @param password        plain-text password
     * @param emailAccountType classification derived from the email domain
     */
    public RegisterAccountData(
            final String firstName,
            final String lastName,
            final String email,
            final String password,
            final EmailAccountType emailAccountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emailAccountType = emailAccountType;
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
     * @return the classified email account type
     */
    public EmailAccountType getEmailAccountType() {
        return this.emailAccountType;
    }
}
