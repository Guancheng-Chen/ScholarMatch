package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FundingStatusTest {

    @Test
    void testAllStatusesPresent() {
        assertEquals(7, FundingStatus.values().length);
    }

    @Test
    void testValueOfKnownStatus() {
        assertNotNull(FundingStatus.valueOf("UNFUNDED"));
    }
}
