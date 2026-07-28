package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.EmailAccountType;
import com.scholarmatch.entity.Posting;
import com.scholarmatch.entity.PostingApplication;
import com.scholarmatch.entity.PostingApplicationStatus;
import com.scholarmatch.entity.PostingStatus;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.AcceptApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ApplyToPostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.ClosePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CreatePostingDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DeclineApplicationDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMyApplicationsDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadPostingsDataAccessInterface;
import com.scholarmatch.usecase.exception.InvalidRequestException;
import com.scholarmatch.usecase.load_postings.PostingScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * In-memory offline implementation of the posting lifecycle (create/close/load) and posting
 * applications (apply/accept/decline/load).
 */
public final class LocalPostingRepository implements
        CreatePostingDataAccessInterface,
        ClosePostingDataAccessInterface,
        LoadPostingsDataAccessInterface,
        ApplyToPostingDataAccessInterface,
        AcceptApplicationDataAccessInterface,
        DeclineApplicationDataAccessInterface,
        LoadMyApplicationsDataAccessInterface {

    private final LocalServerState state;
    private final CurrentUserProviderInterface session;

    public LocalPostingRepository(
            final LocalServerState state,
            final CurrentUserProviderInterface session) {
        this.state = state;
        this.session = session;
    }

    @Override
    public synchronized Posting createPosting(
            final String title,
            final String description,
            final ResearchField researchField,
            final CollaborationType collaborationType,
            final Integer capacity) {
        if (capacity != null && capacity <= 0) {
            throw new InvalidRequestException("Team capacity must be greater than zero");
        }
        final String currentUserId = this.session.getCurrentUserId();
        final User poster = this.state.usersById().get(currentUserId);
        final String posterName = poster == null ? currentUserId : poster.getFullName();
        final boolean academicEmailVerified = poster != null
                && poster.getEmailAccountType() == EmailAccountType.ACADEMIC;
        final Posting posting = new Posting(
                UUID.randomUUID().toString(), currentUserId,
                posterName,
                academicEmailVerified,
                title, description,
                researchField, collaborationType, capacity, 0, 0,
                PostingStatus.OPEN, LocalDateTime.now());
        this.state.postingsById().put(posting.getPostingId(), posting);
        return posting;
    }

    @Override
    public synchronized Posting closePosting(final String postingId) {
        final Posting posting = this.state.postingsById().get(postingId);
        if (posting == null) {
            throw new InvalidRequestException("Posting not found");
        }
        if (!posting.getPosterUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("You are not the poster of this posting");
        }
        if (posting.getStatus() == PostingStatus.CLOSED) {
            throw new InvalidRequestException("Posting is already closed");
        }
        posting.close();
        return posting;
    }

    @Override
    public synchronized List<Posting> loadPostings(final PostingScope scope) {
        final String currentId = this.session.getCurrentUserId();
        final List<Posting> result = new ArrayList<>();
        for (final Posting posting : this.state.postingsById().values()) {
            final boolean mine = posting.getPosterUserId().equals(currentId);
            if (scope == PostingScope.MINE ? mine : !mine && posting.isActive()) {
                result.add(posting);
            }
        }
        return result;
    }

    @Override
    public synchronized Map<String, List<PostingApplication>> loadApplicationsForOwnedPostings(
            final PostingScope scope,
            final List<Posting> postings) {
        if (scope != PostingScope.MINE) {
            return Map.of();
        }
        final String currentId = this.session.getCurrentUserId();
        final Map<String, List<PostingApplication>> result = new LinkedHashMap<>();
        for (final Posting posting : postings) {
            if (!posting.getPosterUserId().equals(currentId)) {
                continue;
            }
            final List<PostingApplication> applications = this.state.applicationsById().values().stream()
                    .filter(application -> application.getPostingId().equals(posting.getPostingId()))
                    .toList();
            result.put(posting.getPostingId(), applications);
        }
        return result;
    }

    @Override
    public synchronized PostingApplication applyToPosting(
            final String postingId,
            final String message) {
        final Posting posting = this.state.postingsById().get(postingId);
        if (posting == null) {
            throw new InvalidRequestException("Posting not found");
        }
        final String currentId = this.session.getCurrentUserId();
        if (posting.getPosterUserId().equals(currentId)) {
            throw new InvalidRequestException("You cannot apply to your own posting");
        }
        if (!posting.isActive()) {
            throw new InvalidRequestException(
                    posting.isFull() ? "This posting is full" : "This posting is closed");
        }
        final boolean duplicate = this.state.applicationsById().values().stream().anyMatch(application ->
                application.getPostingId().equals(postingId)
                        && application.getApplicantUserId().equals(currentId));
        if (duplicate) {
            throw new InvalidRequestException("You have already applied to this posting");
        }
        final PostingApplication application = new PostingApplication(
                UUID.randomUUID().toString(), postingId, currentId,
                message == null ? "" : message, PostingApplicationStatus.PENDING,
                LocalDateTime.now(), posting.getTitle(), this.state.displayName(currentId),
                posting.getPosterUserId(),
                posting.getPosterName(),
                posting.isPosterAcademicEmailVerified());
        this.state.applicationsById().put(application.getApplicationId(), application);
        posting.setApplicantCount(posting.getApplicantCount() + 1);
        return application;
    }

    @Override
    public synchronized PostingApplication acceptApplication(final String applicationId) {
        return reviewApplication(applicationId, PostingApplicationStatus.ACCEPTED);
    }

    @Override
    public synchronized PostingApplication declineApplication(final String applicationId) {
        return reviewApplication(applicationId, PostingApplicationStatus.REJECTED);
    }

    @Override
    public synchronized List<PostingApplication> getMyApplications() {
        final String currentId = this.session.getCurrentUserId();
        return this.state.applicationsById().values().stream()
                .filter(application -> application.getApplicantUserId().equals(currentId))
                .toList();
    }

    private PostingApplication reviewApplication(
            final String applicationId,
            final PostingApplicationStatus newStatus) {
        final PostingApplication application = this.state.applicationsById().get(applicationId);
        if (application == null) {
            throw new InvalidRequestException("Application not found");
        }
        // Every application whose posting is removed is also removed by
        // LocalProfileRepository#deleteAccount(), so a surviving application's posting is
        // always present here.
        final Posting posting = this.state.postingsById().get(application.getPostingId());
        if (!posting.getPosterUserId().equals(this.session.getCurrentUserId())) {
            throw new InvalidRequestException("You are not the poster of this posting");
        }
        if (application.getStatus() != PostingApplicationStatus.PENDING) {
            throw new InvalidRequestException("Application has already been reviewed");
        }
        if (newStatus == PostingApplicationStatus.ACCEPTED && !posting.isActive()) {
            throw new InvalidRequestException(
                    posting.isFull() ? "This posting is full" : "This posting is closed");
        }
        application.setStatus(newStatus);
        if (newStatus == PostingApplicationStatus.ACCEPTED) {
            posting.recordAcceptedApplication();
        }
        return application;
    }
}
