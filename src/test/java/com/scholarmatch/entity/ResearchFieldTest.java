package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResearchFieldTest {

    @Test
    void testAllFieldsPresent() {
        assertEquals(80, ResearchField.values().length);
    }

    @Test
    void testValueOfKnownField() {
        assertNotNull(ResearchField.valueOf("OTHER"));
    }
}
