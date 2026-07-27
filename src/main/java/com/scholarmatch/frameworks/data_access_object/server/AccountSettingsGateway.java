package com.scholarmatch.frameworks.data_access_object.server;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ChangeEmailDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ChangePasswordDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.VerificationEmailSenderDataAccessInterface;

import java.util.Map;

/**
 * Authenticated HTTP adapter for account email and password management.
 */
public final class AccountSettingsGateway
        implements VerificationEmailSenderDataAccessInterface,
        ChangeEmailDataAccessInterface,
        ChangePasswordDataAccessInterface {

    private final ServerHttpClient http;
    private final ScholarUserMapper userMapper;

    public AccountSettingsGateway(
            final ServerHttpClient http,
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.http = http;
        this.userMapper = new ScholarUserMapper(institutionCatalog);
    }

    @Override
    public void requestVerificationCode(final String email) {
        this.http.post(
                "/api/account/email-change/request-code",
                this.http.toJson(Map.of("email", email)),
                true);
    }

    @Override
    public User changeEmail(
            final String email,
            final String currentPassword,
            final String verificationCode) {
        return this.userMapper.fromJson(this.http.put(
                "/api/account/email",
                this.http.toJson(Map.of(
                        "email", email,
                        "currentPassword", currentPassword,
                        "code", verificationCode))));
    }

    @Override
    public void changePassword(
            final String currentPassword,
            final String newPassword) {
        this.http.put(
                "/api/account/password",
                this.http.toJson(Map.of(
                        "currentPassword", currentPassword,
                        "newPassword", newPassword)));
    }
}
