package com.scholarmatch.interface_adapter.view_model;

import com.scholarmatch.interface_adapter.view_model.support.ObservableValue;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.entity.Institution;

import java.util.List;

/**
 * Observable ViewModel for the update-profile screen.
 */
public final class UpdateProfileViewModel {

    private final ObservableValue<String> errorMessage = new ObservableValue<>("");
    private final ObservableValue<String> saveSuccessMessage = new ObservableValue<>("");
    private final ObservableValue<UserData> currentUser = new ObservableValue<>(null);
    private List<Institution> institutions = List.of();

    /**
     * Returns the property holding the current user's full saved profile, populated once
     * com.scholarmatch.interface_adapter.controller.LoadProfileController completes.
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
}
