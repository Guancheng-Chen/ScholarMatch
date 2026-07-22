package com.scholarmatch.frameworks.data_access_object;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClasspathAcademicEmailDomainRepositoryTest {

    private ClasspathAcademicEmailDomainRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ClasspathAcademicEmailDomainRepository();
    }

    @Test
    void testRecognizesUniversityDomain() {
        assertTrue(repository.isAcademicEmail("student@mit.edu"));
        assertEquals("MIT", repository.findInstitutionByEmail("student@mit.edu")
                .orElseThrow().getInstitutionId());
    }

    @Test
    void testRecognizesCaseInsensitiveSubdomain() {
        assertTrue(repository.isAcademicEmail("researcher@CS.UTORONTO.CA"));
    }

    @Test
    void testTreatsUnknownDomainAsRegular() {
        assertFalse(repository.isAcademicEmail("student@gmail.com"));
    }

    @Test
    void testRejectsLookalikeSuffix() {
        assertFalse(repository.isAcademicEmail("student@mit.edu.example.com"));
    }

    @Test
    void testRejectsMalformedAndMissingEmail() {
        assertFalse(repository.isAcademicEmail(null));
        assertFalse(repository.isAcademicEmail("not-an-email"));
        assertFalse(repository.isAcademicEmail("@mit.edu"));
    }
}
