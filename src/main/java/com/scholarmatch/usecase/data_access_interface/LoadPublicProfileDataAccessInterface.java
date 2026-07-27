package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.User;

public interface LoadPublicProfileDataAccessInterface {

    User getPublicProfile(String userId);
}
