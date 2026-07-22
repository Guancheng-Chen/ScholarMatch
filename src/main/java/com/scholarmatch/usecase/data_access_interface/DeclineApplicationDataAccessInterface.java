package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.PostingApplication;

/**
 * Persistence boundary used only when declining an application.
 */
public interface DeclineApplicationDataAccessInterface {
    PostingApplication declineApplication(String applicationId);
}
