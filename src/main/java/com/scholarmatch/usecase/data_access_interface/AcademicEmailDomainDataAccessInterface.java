package com.scholarmatch.usecase.data_access_interface;

/**
 * Boundary for identifying configured academic email domains.
 */
public interface AcademicEmailDomainDataAccessInterface {

    boolean isAcademicEmail(String email);
}
