package com.scholarmatch.usecase.register;

/**
 * Input data record for the register use case.
 *
 * <p>Registration only collects the account-creation essentials — everything else
 * (institution, research field, publications, etc.) is filled in later from the
 * Edit Profile screen once the user is inside the app.
 */
public final class RegisterInputData {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    /**
     * Constructs the register input data.
     *
     * @param firstName given name
     * @param lastName  family name
     * @param email     email address (login credential; must be unique)
     * @param password  plain-text password (hashed by the server)
     */
    public RegisterInputData(
            final String firstName,
            final String lastName,
            final String email,
            final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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
}
