package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import java.util.List;

/**
 * Controller that forwards profile-edit form submission to the update-profile use case.
 */
public final class UpdateProfileController {

    private final UpdateProfileInputBoundary interactor;

    /**
     * Constructs an UpdateProfileController.
     *
     * @param interactor the update-profile use case
     */
    public UpdateProfileController(final UpdateProfileInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Called when the user submits the profile-edit form.
     *
     * <p>The user's identity is resolved server-side from the request's auth token — the
     * interactor itself has no session dependency.
     *
     * @param email                    updated email address; the server rejects this if
     *                                  another account already uses it
     * @param institution              updated institution
     * @param academicLevel            updated academic level name
     * @param researchField            updated research field name
     * @param lookingFor               updated collaboration type name
     * @param collaborationDescription updated description
     * @param researchDescription      updated research description
     * @param weeklyAvailabilityHours  updated weekly availability in hours
     * @param fundingStatus            updated funding status name
     * @param researchInterests        updated interest tags
     * @param phoneNumber              updated phone number
     * @param hIndex                   manually-entered h-index, or null if left blank
     * @param totalCitations           manually-entered total citations, or null if left blank
     * @param educations               updated education history
     * @param publications             updated publication list
     */
    public void updateProfile(
            final String email,
            final String institution,
            final String academicLevel,
            final String researchField,
            final String lookingFor,
            final String collaborationDescription,
            final String researchDescription,
            final Integer weeklyAvailabilityHours,
            final String fundingStatus,
            final List<String> researchInterests,
            final String phoneNumber,
            final Integer hIndex,
            final Integer totalCitations,
            final List<Education> educations,
            final List<Publication> publications) {
        this.interactor.execute(new UpdateProfileInputData(
                email, institution, academicLevel, researchField, lookingFor,
                collaborationDescription, researchDescription, weeklyAvailabilityHours, fundingStatus,
                researchInterests, phoneNumber, hIndex, totalCitations, educations, publications));
    }
}
