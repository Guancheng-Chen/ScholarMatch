package com.scholarmatch.frameworks.data_access_object.local_mock_server;

/**
 * Boundary for identifying configured academic email domains.
 */
public interface AcademicEmailDomainDataAccessInterface {

    boolean isAcademicEmail(String email);
}
