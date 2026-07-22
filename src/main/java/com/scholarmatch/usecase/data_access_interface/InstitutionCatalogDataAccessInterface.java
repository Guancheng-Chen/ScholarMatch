package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Institution;

import java.util.List;

/**
 * Read-only port for the configured institution reference catalog.
 */
public interface InstitutionCatalogDataAccessInterface {
    List<Institution> getAllInstitutions();

    Institution findById(String institutionId);
}
