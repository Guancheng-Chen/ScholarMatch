package com.scholarmatch.usecase.data_access_interface;

import com.scholarmatch.entity.PostingApplication;

import java.util.List;

/**
 * Query boundary for the current user's submitted applications.
 */
public interface LoadMyApplicationsDataAccessInterface {
    List<PostingApplication> getMyApplications();
}
