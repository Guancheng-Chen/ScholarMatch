package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.Posting;

public interface ClosePostingDataAccessInterface {
    Posting closePosting(String postingId);
}
