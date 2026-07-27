package com.scholarmatch.usecase.load_public_profile;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.LoadPublicProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class LoadPublicProfileInteractorTest {

    @Test
    void testLoadsOnlyPublicProfileFields() {
        final LoadPublicProfileDataAccessInterface dataAccessObject =
                mock(LoadPublicProfileDataAccessInterface.class);
        final LoadPublicProfileOutputBoundary outputBoundary =
                mock(LoadPublicProfileOutputBoundary.class);
        when(dataAccessObject.getPublicProfile("owner-1"))
                .thenReturn(owner());
        final LoadPublicProfileInteractor interactor =
                new LoadPublicProfileInteractor(dataAccessObject, outputBoundary);

        interactor.execute(new LoadPublicProfileInputData("owner-1"));

        final ArgumentCaptor<LoadPublicProfileOutputData> result =
                ArgumentCaptor.forClass(LoadPublicProfileOutputData.class);
        verify(outputBoundary).prepareSuccessView(result.capture());
        assertEquals("owner-1", result.getValue().userId());
        assertEquals("Ada", result.getValue().firstName());
        assertEquals(Institution.UNIVERSITY_OF_TORONTO,
                result.getValue().institution());
        assertTrue(result.getValue().academicEmailVerified());
        final var componentNames = Arrays.stream(
                        LoadPublicProfileOutputData.class.getRecordComponents())
                .map(component -> component.getName())
                .toList();
        assertFalse(componentNames.contains("email"));
        assertFalse(componentNames.contains("phoneNumber"));
        assertFalse(componentNames.contains("passwordHash"));
    }

    @Test
    void testRejectsMissingOwnerIdWithoutLoadingData() {
        final LoadPublicProfileDataAccessInterface dataAccessObject =
                mock(LoadPublicProfileDataAccessInterface.class);
        final LoadPublicProfileOutputBoundary outputBoundary =
                mock(LoadPublicProfileOutputBoundary.class);
        final LoadPublicProfileInteractor interactor =
                new LoadPublicProfileInteractor(dataAccessObject, outputBoundary);

        interactor.execute(new LoadPublicProfileInputData(" "));

        verify(outputBoundary).prepareFailView(
                "The posting owner is unavailable.");
        verifyNoInteractions(dataAccessObject);
    }

    @Test
    void testPresentsMissingAndUnauthorizedFailures() {
        final LoadPublicProfileDataAccessInterface dataAccessObject =
                mock(LoadPublicProfileDataAccessInterface.class);
        final LoadPublicProfileOutputBoundary outputBoundary =
                mock(LoadPublicProfileOutputBoundary.class);
        when(dataAccessObject.getPublicProfile("missing"))
                .thenThrow(new ResourceNotFoundException("Public profile not found"));
        final LoadPublicProfileInteractor interactor =
                new LoadPublicProfileInteractor(dataAccessObject, outputBoundary);

        interactor.execute(new LoadPublicProfileInputData("missing"));

        verify(outputBoundary).prepareFailView("Public profile not found");
    }

    private User owner() {
        return new User(
                "owner-1", "Ada", "Lovelace", "private@example.com",
                "555-1234", Institution.UNIVERSITY_OF_TORONTO,
                AcademicLevel.UNDERGRADUATE, ResearchField.COMPUTER_SCIENCE,
                CollaborationType.RESEARCH_GROUP, "Build a project",
                "Software engineering", 8, FundingStatus.OTHER,
                "private-password", EmailAccountType.ACADEMIC);
    }
}
