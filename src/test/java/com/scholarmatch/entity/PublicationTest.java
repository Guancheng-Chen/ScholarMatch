package com.scholarmatch.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PublicationTest {

    @Test
    void testGetters() {
        final Publication publication = new Publication("10.1145/12345",
                "Attention Is All You Need", 2017, 50000);
        assertEquals("10.1145/12345", publication.getDoi());
        assertEquals("Attention Is All You Need", publication.getTitle());
        assertEquals(2017, publication.getYear());
        assertEquals(50000, publication.getCitationCount());
    }
}
