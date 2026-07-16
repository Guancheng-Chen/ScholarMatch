package com.scholarmatch.usecase.login;

/**
 * Input data for the login use case.
 */
public final class LoginInputData {

    private final String email;
    private final String password;

    /**
     * Constructs login input data.
     *
     * @param email    the email address entered by the user
     * @param password the plain-text password entered by the user
     */
    public LoginInputData(final String email, final String password) {
        this.email = email;
        this.password = password;
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
}

