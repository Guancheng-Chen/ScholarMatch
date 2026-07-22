package com.scholarmatch.usecase.create_posting;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.ResearchField;

public record CreatePostingInputData(
        String title,
        String description,
        ResearchField researchField,
        CollaborationType collaborationType,
        Integer capacity) {
}
