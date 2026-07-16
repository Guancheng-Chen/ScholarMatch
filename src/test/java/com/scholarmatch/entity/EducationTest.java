package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EducationTest {

    @Test
    void testOngoingWhenEndYearIsNull() {
        final Education education =
                new Education("University of Toronto", DegreeType.MASTER, 2023, Month.SEPTEMBER, null, null);

        assertTrue(education.isOngoing());
    }

    @Test
    void testNotOngoingWhenEndYearIsSet() {
        final Education education =
                new Education("University of Toronto", DegreeType.BACHELOR, 2018, Month.SEPTEMBER, 2022, Month.JUNE);

        assertFalse(education.isOngoing());
        assertEquals(2022, education.getEndYear());
    }
}
