package com.scholarmatch.usecase.update_profile;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateProfileInputDataTest {

    @Test
    void preservesUnknownMetricsAndCopiesLists() {
        final List<String> interests = new ArrayList<>();
        final UpdateProfileInputData inputData = new UpdateProfileInputData(
                "researcher@example.com",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                interests,
                List.of(),
                List.of(),
                null,
                null);

        interests.add("machine learning");
        inputData.getResearchInterests().add("data science");

        assertTrue(inputData.getResearchInterests().isEmpty());
        assertNull(inputData.getHIndex());
        assertNull(inputData.getTotalCitations());
    }
}
