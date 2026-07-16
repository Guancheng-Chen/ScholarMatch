package com.scholarmatch.usecase.paper_lookup;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorCandidateDataTest {

    @Test
    void preservesUnknownMetricsAndCopiesAffiliations() {
        final List<String> affiliations = new ArrayList<>(List.of("University of Toronto"));
        final AuthorCandidateData candidate = new AuthorCandidateData(
                "123",
                "Ada Lovelace",
                affiliations,
                null,
                null,
                null);

        affiliations.clear();
        candidate.getAffiliations().add("Other Institution");

        assertEquals(List.of("University of Toronto"), candidate.getAffiliations());
        assertNull(candidate.getPaperCount());
        assertNull(candidate.getHIndex());
        assertNull(candidate.getCitationCount());
    }
}
