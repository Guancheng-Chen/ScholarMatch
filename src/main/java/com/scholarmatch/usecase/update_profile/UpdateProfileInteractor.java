package com.scholarmatch.usecase.update_profile;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.User;
import com.scholarmatch.usecase.data_access_interface.UpdateProfileDataAccessInterface;
import com.scholarmatch.usecase.exception.DataAccessException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Interactor implementing the update-profile use case.
 *
 * <p>Delegates field updates and re-embedding to the server via
 * UpdateProfileDataAccessInterface. Before doing so, it runs a local validation pass over
 * every field the client can already judge without the server (lengths, formats, education
 * date sanity) and collects every violation found, rather than stopping at the first one, so
 * the caller can present the user with the complete list in one shot. The current user's
 * identity is resolved server-side from the request's auth token, so this interactor has no
 * session dependency.
 */
public final class UpdateProfileInteractor implements UpdateProfileInputBoundary {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final int MAX_DESCRIPTION_LENGTH = 2000;
    private static final int MAX_INTEREST_LENGTH = 60;
    private static final int MIN_EDUCATION_YEAR = 1900;
    private static final int MAX_FUTURE_YEARS = 10;

    private final UpdateProfileDataAccessInterface profileDataAccessObject;
    private final UpdateProfileOutputBoundary outputBoundary;

    /**
     * Constructs an UpdateProfileInteractor.
     *
     * @param profileDataAccessObject server gateway for profile writes
     * @param outputBoundary          the presenter that receives the result
     */
    public UpdateProfileInteractor(
            final UpdateProfileDataAccessInterface profileDataAccessObject,
            final UpdateProfileOutputBoundary outputBoundary) {
        this.profileDataAccessObject = profileDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(final UpdateProfileInputData inputData) {
        final List<String> errors = validate(inputData);
        if (!errors.isEmpty()) {
            outputBoundary.prepareFailView(String.join("\n", errors));
            return;
        }
        try {
            final User updated = profileDataAccessObject.updateProfile(inputData);
            outputBoundary.prepareSuccessView(new UpdateProfileOutputData(updated.getUserId()));
        } catch (final DataAccessException e) {
            outputBoundary.prepareFailView(e.getMessage());
        }
    }

    /**
     * Runs every local (non-server) validation rule against the given input, returning
     * every violation found. An empty list means the input is locally valid and can be
     * sent to the server.
     */
    private List<String> validate(final UpdateProfileInputData inputData) {
        final List<String> errors = new ArrayList<>();

        final String email = inputData.getEmail();
        if (email == null || email.isBlank()) {
            errors.add("Email is required.");
        } else if (!isValidEmail(email)) {
            errors.add("Email format is invalid, e.g. name@example.com.");
        }

        requireText(errors, "Phone number", inputData.getPhoneNumber());
        requireText(errors, "Institution", inputData.getInstitution());
        requireText(errors, "Academic level", inputData.getAcademicLevel());
        requireText(errors, "Research field", inputData.getResearchField());
        requireText(errors, "Looking for", inputData.getLookingFor());
        requireText(errors, "Funding status", inputData.getFundingStatus());

        requireText(errors, "Collaboration description", inputData.getCollaborationDescription());
        requireText(errors, "Research description", inputData.getResearchDescription());
        checkLength(errors, "Collaboration description", inputData.getCollaborationDescription());
        checkLength(errors, "Research description", inputData.getResearchDescription());

        if (inputData.getWeeklyAvailabilityHours() == null) {
            errors.add("Weekly availability is required.");
        }
        if (inputData.getHIndex() == null) {
            errors.add("h-index is required.");
        }
        if (inputData.getTotalCitations() == null) {
            errors.add("Total citations is required.");
        }

        if (inputData.getResearchInterests().isEmpty()) {
            errors.add("At least one research interest is required.");
        }
        for (final String interest : inputData.getResearchInterests()) {
            if (interest != null && interest.length() > MAX_INTEREST_LENGTH) {
                errors.add("Research interest \"" + interest + "\" must be at most "
                        + MAX_INTEREST_LENGTH + " characters (currently " + interest.length() + ").");
            }
        }

        validateEducations(errors, inputData.getEducations());

        return errors;
    }

    private void requireText(final List<String> errors, final String fieldName, final String value) {
        if (value == null || value.isBlank()) {
            errors.add(fieldName + " is required.");
        }
    }

    private void checkLength(final List<String> errors, final String fieldName, final String value) {
        if (value != null && value.length() > MAX_DESCRIPTION_LENGTH) {
            errors.add(fieldName + " must be at most " + MAX_DESCRIPTION_LENGTH
                    + " characters (currently " + value.length() + ").");
        }
    }

    private void validateEducations(final List<String> errors, final List<Education> educations) {
        final int maxYear = Year.now().getValue() + MAX_FUTURE_YEARS;
        for (int i = 0; i < educations.size(); i++) {
            final Education education = educations.get(i);
            final String label = educationLabel(i, education);

            if (education.getStartYear() < MIN_EDUCATION_YEAR || education.getStartYear() > maxYear) {
                errors.add(label + ": start year " + education.getStartYear() + " is out of range ("
                        + MIN_EDUCATION_YEAR + "–" + maxYear + ").");
            }

            if (education.isOngoing()) {
                continue;
            }

            final int endYear = education.getEndYear();
            if (endYear < MIN_EDUCATION_YEAR || endYear > maxYear) {
                errors.add(label + ": end year " + endYear + " is out of range ("
                        + MIN_EDUCATION_YEAR + "–" + maxYear + ").");
                continue;
            }

            if (education.getEndMonth() == null) {
                errors.add(label + ": end month is required, or mark this entry as currently enrolled.");
                continue;
            }

            final boolean endsBeforeStart = endYear < education.getStartYear()
                    || (endYear == education.getStartYear()
                    && education.getEndMonth().getValue() < education.getStartMonth().getValue());
            if (endsBeforeStart) {
                errors.add(label + ": end date cannot be before the start date.");
            }
        }
    }

    private String educationLabel(final int index, final Education education) {
        final String institution = education.getInstitution();
        final String suffix = institution == null || institution.isBlank() ? "" : " (" + institution + ")";
        return "Education #" + (index + 1) + suffix;
    }

    private boolean isValidEmail(final String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
