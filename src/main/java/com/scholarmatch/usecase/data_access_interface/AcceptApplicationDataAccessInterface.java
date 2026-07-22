package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.PostingApplication;

/**
 * Persistence boundary used only when accepting an application.
 */
public interface AcceptApplicationDataAccessInterface {
    PostingApplication acceptApplication(String applicationId);
}
