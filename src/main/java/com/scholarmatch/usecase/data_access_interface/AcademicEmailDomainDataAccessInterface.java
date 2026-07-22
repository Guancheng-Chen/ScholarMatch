package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Institution;

import java.util.Optional;

/**
 * Port for checking whether an email belongs to a recognized university domain.
 */
public interface AcademicEmailDomainDataAccessInterface {

    /**
     * Checks an email address against the configured university domain catalog.
     *
     * @param email the complete email address
     * @return true when the domain or one of its parent domains is recognized
     */
    boolean isAcademicEmail(String email);

    default Optional<Institution> findInstitutionByEmail(final String email) {
        return Optional.empty();
    }
}
