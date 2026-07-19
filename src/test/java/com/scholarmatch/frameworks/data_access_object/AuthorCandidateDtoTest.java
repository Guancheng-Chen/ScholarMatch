package com.scholarmatch.frameworks.data_access_object;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorCandidateDtoTest {

    @Test
    void testGettersReturnConstructorValues() {
        final AuthorCandidateDto dto = new AuthorCandidateDto(
                "hinton-local",
                "Geoffrey Hinton",
                List.of("University of Toronto"),
                466,
                135,
                900000
        );

        assertEquals("hinton-local", dto.getAuthorId());
        assertEquals("Geoffrey Hinton", dto.getName());
        assertEquals(List.of("University of Toronto"), dto.getAffiliations());
        assertEquals(466, dto.getPaperCount());
        assertEquals(135, dto.getHIndex());
        assertEquals(900000, dto.getCitationCount());
    }

    @Test
    void testHIndexAndCitationCountMayBeNull() {
        final AuthorCandidateDto dto = new AuthorCandidateDto(
                "unknown-id",
                "Jane Doe",
                List.of(),
                0,
                null,
                null
        );

        assertNull(dto.getHIndex());
        assertNull(dto.getCitationCount());
    }
}
