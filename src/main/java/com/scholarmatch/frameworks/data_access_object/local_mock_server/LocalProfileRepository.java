package com.scholarmatch.frameworks.data_access_object.local_mock_server;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DeleteAccountDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.InstitutionCatalogDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadProfileDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import java.util.HashSet;
import java.util.Set;

/**
 * In-memory offline implementation of reading, updating, and deleting the current user's
 * profile.
 */
public final class LocalProfileRepository implements
        LoadProfileDataAccessInterface,
        UpdateProfileDataAccessInterface,
        DeleteAccountDataAccessInterface {

    private final LocalServerState state;
    private final CurrentUserProviderInterface session;
    private final InstitutionCatalogDataAccessInterface institutionCatalog;

    public LocalProfileRepository(
            final LocalServerState state,
            final CurrentUserProviderInterface session,
            final InstitutionCatalogDataAccessInterface institutionCatalog) {
        this.state = state;
        this.session = session;
        this.institutionCatalog = institutionCatalog;
    }

    @Override
    public User getProfile() {
        final User user = this.state.usersById().get(this.session.getCurrentUserId());
        if (user == null) {
            throw new ResourceNotFoundException("No profile found for the current user");
        }
        return user;
    }

    @Override
    public User updateProfile(final UpdateProfileInputData data) {
        final User user = getProfile();
        if (data.getInstitution() != null) {
            user.setInstitution(parseInstitution(data.getInstitution()));
        }
        if (data.getAcademicLevel() != null) {
            user.setAcademicLevel(parseAcademicLevel(data.getAcademicLevel()));
        }
        if (data.getResearchField() != null) {
            user.setResearchField(parseResearchField(data.getResearchField()));
        }
        if (data.getLookingFor() != null) {
            user.setLookingFor(parseCollaborationType(data.getLookingFor()));
        }
        if (data.getCollaborationDescription() != null) {
            user.setCollaborationDescription(data.getCollaborationDescription());
        }
        if (data.getResearchDescription() != null) {
            user.setResearchDescription(data.getResearchDescription());
        }
        if (data.getWeeklyAvailabilityHours() != null) {
            user.setWeeklyAvailabilityHours(data.getWeeklyAvailabilityHours());
        }
        if (data.getFundingStatus() != null) {
            user.setFundingStatus(parseFundingStatus(data.getFundingStatus()));
        }
        if (data.getPhoneNumber() != null) {
            user.setPhoneNumber(data.getPhoneNumber());
        }
        if (data.getHIndex() != null) {
            user.sethIndex(data.getHIndex());
        }
        if (data.getTotalCitations() != null) {
            user.setTotalCitations(data.getTotalCitations());
        }
        for (final String interest : user.getResearchInterests()) {
            user.removeResearchInterest(interest);
        }
        for (final String interest : data.getResearchInterests()) {
            user.addResearchInterest(interest);
        }
        for (final Publication publication : user.getPublications()) {
            user.removePublication(publication.getDoi());
        }
        for (final Publication publication : data.getPublications()) {
            user.addPublication(publication);
        }
        return user;
    }

    @Override
    public void deleteAccount() {
        final String currentId = this.session.getCurrentUserId();
        this.state.usersById().remove(currentId);
        this.state.recordedConnections().removeIf(key -> key.contains(currentId));
        this.state.recordedDislikes().removeIf(key -> key.contains(currentId));
        this.state.messages().removeIf(message ->
                message.getSenderId().equals(currentId) || message.getReceiverId().equals(currentId));
        final Set<String> removedPostingIds = new HashSet<>();
        this.state.postingsById().values().removeIf(posting -> {
            final boolean remove = posting.getPosterUserId().equals(currentId);
            if (remove) {
                removedPostingIds.add(posting.getPostingId());
            }
            return remove;
        });
        // A user can never apply to their own posting, so an application made by currentId can
        // never point at one of the postings just removed above — its posting always survives.
        for (final PostingApplication application : this.state.applicationsById().values()) {
            if (application.getApplicantUserId().equals(currentId)) {
                final Posting posting = this.state.postingsById().get(application.getPostingId());
                posting.setApplicantCount(Math.max(0, posting.getApplicantCount() - 1));
            }
        }
        this.state.applicationsById().values().removeIf(application ->
                application.getApplicantUserId().equals(currentId)
                        || removedPostingIds.contains(application.getPostingId()));
    }

    private AcademicLevel parseAcademicLevel(final String value) {
        try {
            return AcademicLevel.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return AcademicLevel.UNDERGRADUATE;
        }
    }

    private CollaborationType parseCollaborationType(final String value) {
        try {
            return CollaborationType.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return CollaborationType.INTEREST_SHARING;
        }
    }

    private ResearchField parseResearchField(final String value) {
        try {
            return ResearchField.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return ResearchField.OTHER;
        }
    }

    private FundingStatus parseFundingStatus(final String value) {
        try {
            return FundingStatus.valueOf(value);
        } catch (final IllegalArgumentException e) {
            return FundingStatus.OTHER;
        }
    }

    private Institution parseInstitution(final String value) {
        return this.institutionCatalog.findById(value);
    }
}
