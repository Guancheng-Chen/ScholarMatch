package com.scholarmatch.interface_adapter.controller;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import java.util.List;

/**
 * Controller for the update-profile use case.
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
     * Submits updated profile information.
     *
     * @param email updated email
     * @param institution updated institution
     * @param academicLevel updated academic level
     * @param researchField updated research field
     * @param lookingFor updated collaboration type
     * @param collaborationDescription updated collaboration description
     * @param researchDescription updated research description
     * @param weeklyAvailabilityHours updated weekly availability
     * @param fundingStatus updated funding status
     * @param researchInterests updated research interests
     * @param phoneNumber updated phone number
     * @param hIndex updated h-index
     * @param totalCitations updated citation count
     * @param educations updated education history
     * @param publications updated publications
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
                email,
                institution,
                academicLevel,
                researchField,
                lookingFor,
                collaborationDescription,
                researchDescription,
                weeklyAvailabilityHours,
                fundingStatus,
                researchInterests,
                phoneNumber,
                hIndex,
                totalCitations,
                educations,
                publications
        ));
    }
}
