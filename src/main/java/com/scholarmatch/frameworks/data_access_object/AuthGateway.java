package com.scholarmatch.frameworks.data_access_object;

import com.fasterxml.jackson.databind.JsonNode;
import com.scholarmatch.usecase.data_access_interface.AuthResult;
import com.scholarmatch.usecase.data_access_interface.LoginDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RegisterDataAccessInterface;
import com.scholarmatch.usecase.register.RegisterAccountData;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP implementation of login and registration — the "auth" actor's slice of what used to
 * be ServerRepository. Doesn't touch profile, matching, messaging, or postings; those are
 * ProfileGateway, MatchingGateway, MessagingGateway, and PostingGateway.
 */
public final class AuthGateway implements LoginDataAccessInterface, RegisterDataAccessInterface {

    private final ServerHttpClient http;

    public AuthGateway(final ServerHttpClient http) {
        this.http = http;
    }

    @Override
    public AuthResult login(final String email, final String password) {
        final String body = this.http.toJson(Map.of("email", email, "password", password));
        final JsonNode node = this.http.post("/api/auth/login", body, false);
        return authResultFromJson(node);
    }

    @Override
    public AuthResult register(final RegisterAccountData data) {
        // Registration only collects the account-creation essentials; every other profile
        // field is filled in later from the Edit Profile screen once the user is in the app.
        final Map<String, Object> body = new HashMap<>();
        body.put("firstName", data.getFirstName());
        body.put("lastName", data.getLastName());
        body.put("email", data.getEmail());
        body.put("password", data.getPassword());
        body.put("code", data.getVerificationCode());

        final JsonNode node = this.http.post("/api/auth/register", this.http.toJson(body), false);
        return authResultFromJson(node);
    }

    private AuthResult authResultFromJson(final JsonNode node) {
        return new AuthResult(
                node.get("token").asText(),
                node.get("scholarId").asText(),
                node.get("name").asText());
    }
}
