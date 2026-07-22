package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.ResearchField;

/**
 * Persistence boundary used only when creating a posting.
 */
public interface CreatePostingDataAccessInterface {
    Posting createPosting(
            String title,
            String description,
            ResearchField researchField,
            CollaborationType collaborationType,
            Integer capacity);
}
