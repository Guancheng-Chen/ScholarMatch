package com.scholarmatch.usecase.paper_lookup;

import com.scholarmatch.frameworks.data_access_object.AuthorCandidateDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorCandidateDataTest {

    @Test
    void createsOutputDataFromGatewayData() {
        final AuthorCandidateData candidate = AuthorCandidateData.from(new AuthorCandidateDto(
                "123",
                "Ada Lovelace",
                List.of("Analytical Engine Institute"),
                4,
                3,
                25));

        assertEquals("123", candidate.getAuthorId());
        assertEquals("Ada Lovelace", candidate.getName());
        assertEquals(List.of("Analytical Engine Institute"), candidate.getAffiliations());
        assertEquals(4, candidate.getPaperCount());
        assertEquals(3, candidate.getHIndex());
        assertEquals(25, candidate.getCitationCount());
    }

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
