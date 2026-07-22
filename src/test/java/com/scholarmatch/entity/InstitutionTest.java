package com.scholarmatch.entity;

import com.scholarmatch.frameworks.data_access_object.ClasspathInstitutionCatalogRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InstitutionTest {

    @Test
    void testTotalCount() {
        assertEquals(1029, new ClasspathInstitutionCatalogRepository().getAllInstitutions().size());
    }

    @Test
    void testWellKnownConstantsResolve() {
        final ClasspathInstitutionCatalogRepository catalog =
                new ClasspathInstitutionCatalogRepository();
        assertNotNull(catalog.findById("MIT"));
        assertNotNull(catalog.findById("UNIVERSITY_OF_TORONTO"));
        assertNotNull(catalog.findById(
                "CERN_EUROPEAN_ORGANIZATION_FOR_NUCLEAR_RESEARCH"));
        assertNotNull(catalog.findById("OTHER"));
    }

    @Test
    void testDisplayName() {
        assertEquals(
                "Massachusetts Institute of Technology",
                Institution.MIT.getDisplayName());
    }
}
