package com.scholarmatch.interface_adapter.view_model.update_profile;

import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;

import java.util.List;

/**
 * Observable ViewModel for the update-profile screen.
 */
public final class UpdateProfileViewModel {

    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> saveSuccessMessage = new ObservableValue<>("");
    private final ObservableValue<UserData> currentUser = new ObservableValue<>(null);
    private List<Institution> institutions = List.of();
    private final List<AcademicLevel> academicLevels = List.of(AcademicLevel.values());
    private final List<CollaborationType> collaborationTypes = List.of(CollaborationType.values());
    private final List<ResearchField> researchFields = List.of(ResearchField.values());
    private final List<FundingStatus> fundingStatuses = List.of(FundingStatus.values());

    /**
     * Returns the property holding the current user's full saved profile, populated once
     * com.scholarmatch.interface_adapter.load_profile.LoadProfileController completes.
     * The view listens to this to pre-fill the edit form instead of starting blank.
     *
     * @return the current-user property
     */
    public ObservableValue<UserData> currentUserProperty() {
        return this.currentUser;
    }

    /**
     * @param user the loaded (or just-saved) full profile
     */
    public void setCurrentUser(final UserData user) {
        this.currentUser.set(user);
    }

    /**
     * @return the error message property
     */
    public ObservableValue<String> errorMessageProperty() {
        return this.errorMessage;
    }

    /**
     * @param message the error text to display
     */
    public void setErrorMessage(final String message) {
        this.errorMessage.set(message);
    }

    /**
     * @return the save-success message property, set once per successful save so the view
     *     can pop up a confirmation
     */
    public ObservableValue<String> saveSuccessMessageProperty() {
        return this.saveSuccessMessage;
    }

    /**
     * @param message the confirmation text to display after a successful save
     */
    public void setSaveSuccessMessage(final String message) {
        this.saveSuccessMessage.set(message);
    }

    public List<Institution> getInstitutions() {
        return this.institutions;
    }

    public void setInstitutions(final List<Institution> institutions) {
        this.institutions = List.copyOf(institutions);
    }

    /**
     * Returns every selectable {@link AcademicLevel}, for the view to populate its dropdown
     * without needing to call {@code AcademicLevel.values()} itself.
     *
     * @return the full set of academic levels
     */
    public List<AcademicLevel> getAcademicLevels() {
        return this.academicLevels;
    }

    /**
     * Returns every selectable {@link CollaborationType}, for the view to populate its
     * dropdown without needing to call {@code CollaborationType.values()} itself.
     *
     * @return the full set of collaboration types
     */
    public List<CollaborationType> getCollaborationTypes() {
        return this.collaborationTypes;
    }

    /**
     * Returns every selectable {@link ResearchField}, for the view to populate its dropdown
     * without needing to call {@code ResearchField.values()} itself.
     *
     * @return the full set of research fields
     */
    public List<ResearchField> getResearchFields() {
        return this.researchFields;
    }

    /**
     * Returns every selectable {@link FundingStatus}, for the view to populate its dropdown
     * without needing to call {@code FundingStatus.values()} itself.
     *
     * @return the full set of funding statuses
     */
    public List<FundingStatus> getFundingStatuses() {
        return this.fundingStatuses;
    }
}
