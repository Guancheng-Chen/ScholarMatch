package com.scholarmatch.usecase.data_access_interface;

/**
 * Carries the server's response to a successful login or registration.
 *
 * <p>Extracted as its own type (rather than nested in one auth-related interface) so both
 * LoginDataAccessInterface and RegisterDataAccessInterface can share it
 * without either one depending on the other.
 *
 * @param token       the JWT Bearer token for subsequent authenticated requests
 * @param userId      the unique identifier of the authenticated user
 * @param displayName the user's full display name (firstName + " " + lastName)
 */
public record AuthResult(String token, String userId, String displayName) { }
