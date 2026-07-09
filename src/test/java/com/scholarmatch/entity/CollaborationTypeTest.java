package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CollaborationTypeTest {

    @Test
    void testAllCollaborationTypesPresent() {
        assertEquals(5, CollaborationType.values().length);
    }

    @Test
    void testValueOfKnownType() {
        assertNotNull(CollaborationType.valueOf("MENTORSHIP"));
    }
}
