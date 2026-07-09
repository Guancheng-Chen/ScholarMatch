package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AcademicLevelTest {

    @Test
    void testAllLevelsPresent() {
        assertEquals(5, AcademicLevel.values().length);
    }

    @Test
    void testValueOfKnownLevel() {
        assertNotNull(AcademicLevel.valueOf("FACULTY"));
    }
}
