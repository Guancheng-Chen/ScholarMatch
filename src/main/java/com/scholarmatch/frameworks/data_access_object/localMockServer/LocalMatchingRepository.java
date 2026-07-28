package com.scholarmatch.frameworks.data_access_object.localMockServer;

import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.ConnectDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.CurrentUserProviderInterface;
import com.scholarmatch.usecase.data_access_interface.DislikeDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.LoadMatchesDataAccessInterface;
import com.scholarmatch.usecase.data_access_interface.RecommendDataAccessInterface;
import com.scholarmatch.usecase.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory offline implementation of recommending, connecting with, disliking, and loading
 * matched users.
 */
public final class LocalMatchingRepository implements
        RecommendDataAccessInterface,
        ConnectDataAccessInterface,
        DislikeDataAccessInterface,
        LoadMatchesDataAccessInterface {

    private final LocalServerState state;
    private final CurrentUserProviderInterface session;

    public LocalMatchingRepository(
            final LocalServerState state,
            final CurrentUserProviderInterface session) {
        this.state = state;
        this.session = session;
    }

    @Override
    public List<User> getRecommendations() {
        final String currentId = this.session.getCurrentUserId();
        final List<User> recommendations = new ArrayList<>();
        for (final User user : this.state.usersById().values()) {
            final String otherId = user.getUserId();
            final boolean alreadyDisliked =
                    this.state.recordedDislikes().contains(currentId + "->" + otherId);
            if (!otherId.equals(currentId) && !alreadyDisliked) {
                recommendations.add(user);
            }
        }
        return recommendations;
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
    public boolean connect(final String connectedUserId) {
        final String currentId = this.session.getCurrentUserId();
        if (this.state.seedUserIds().contains(connectedUserId)
                && this.state.recordedConnections().contains(connectedUserId + "->" + currentId)) {
            // Seed users always "connect back" instantly — record both directions so
            // this match also shows up later via getMatches(), not just in this moment.
            this.state.recordedConnections().add(currentId + "->" + connectedUserId);
            this.state.recordedConnections().add(connectedUserId + "->" + currentId);
            return true;
        }
        this.state.recordedConnections().add(currentId + "->" + connectedUserId);
        return this.state.hasMutualConnection(currentId, connectedUserId);
    }

    @Override
    public void dislike(final String dislikedUserId) {
        final String currentId = this.session.getCurrentUserId();
        this.state.recordedDislikes().add(currentId + "->" + dislikedUserId);
    }

    @Override
    public List<User> getMatches() {
        final String currentId = this.session.getCurrentUserId();
        final List<User> matches = new ArrayList<>();
        for (final User user : this.state.usersById().values()) {
            final String otherId = user.getUserId();
            final boolean mutualMatch = !otherId.equals(currentId)
                    && this.state.hasMutualConnection(currentId, otherId);
            if (mutualMatch) {
                matches.add(user);
            }
        }
        return matches;
    }
}
