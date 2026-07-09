package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EducationTest {
    @Test
    void testOngoingWhenEndYearIsZero() {
        final Education education = new Education("University of Toronto", DegreeType.MASTER, 2023, 9, 0, 0);

        assertTrue(education.isOngoing());
    }

    @Test
    void testNotOngoingWhenEndYearIsSet() {
        final Education education = new Education("University of Toronto", DegreeType.BACHELOR, 2018, 9, 2022, 6);

        assertFalse(education.isOngoing());
        assertEquals(2022, education.getEndYear());
    }
}
