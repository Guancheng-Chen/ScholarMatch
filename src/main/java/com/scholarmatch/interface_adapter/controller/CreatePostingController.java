package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.usecase.create_posting.CreatePostingInputBoundary;
import com.scholarmatch.usecase.create_posting.CreatePostingInputData;

public final class CreatePostingController {
    private final CreatePostingInputBoundary interactor;

    public CreatePostingController(final CreatePostingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity) {
        this.interactor.execute(new CreatePostingInputData(
                title, description, researchField, collaborationType, capacity));
    }

    public void createPosting(
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity) {
        execute(title, description, researchField, collaborationType, capacity);
    }
}
