package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InstitutionTest {

    @Test
    void testTotalCount() {
        assertEquals(1029, Institution.values().length);
    }

    @Test
    void testWellKnownConstantsResolve() {
        assertNotNull(Institution.valueOf("MIT"));
        assertNotNull(Institution.valueOf("UNIVERSITY_OF_TORONTO"));
        assertNotNull(Institution.valueOf(
                "CERN_EUROPEAN_ORGANIZATION_FOR_NUCLEAR_RESEARCH"));
        assertNotNull(Institution.valueOf("OTHER"));
    }

    @Test
    void testDisplayName() {
        assertEquals(
                "Massachusetts Institute of Technology",
                Institution.MIT.getDisplayName());
    }
}
