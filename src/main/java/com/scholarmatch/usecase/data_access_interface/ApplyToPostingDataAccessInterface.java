package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.PostingApplication;

/**
 * Persistence boundary used only when applying to a posting.
 */
public interface ApplyToPostingDataAccessInterface {
    PostingApplication applyToPosting(String postingId, String message);
}
