package com.scholarmatch.frameworks.data_access_object.localMockServer;

/**
 * Boundary for identifying configured academic email domains.
 */
public interface AcademicEmailDomainDataAccessInterface {

    boolean isAcademicEmail(String email);
}
