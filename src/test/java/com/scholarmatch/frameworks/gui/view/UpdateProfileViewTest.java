package com.scholarmatch.frameworks.gui.view;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.entity.User;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.interface_adapter.controller.LoadProfileController;
import com.scholarmatch.interface_adapter.controller.PaperLookupController;
import com.scholarmatch.interface_adapter.controller.UpdateProfileController;
import com.scholarmatch.interface_adapter.view_model.PaperLookupViewModel;
import com.scholarmatch.interface_adapter.view_model.UpdateProfileViewModel;
import com.scholarmatch.usecase.dto.UserData;
import com.scholarmatch.usecase.load_profile.LoadProfileInputBoundary;
import com.scholarmatch.usecase.paper_lookup.PaperLookupInputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputBoundary;
import com.scholarmatch.usecase.update_profile.UpdateProfileInputData;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class UpdateProfileViewTest {

    @Test
    void testInstitutionDropdownIsSortedAlphabeticallyWithOtherLast() {
        final UpdateProfileView view = buildView(mock(UpdateProfileInputBoundary.class));

        final JComboBox<Institution> institutionCombo = SwingTestSupport.find(view, JComboBox.class, 0);
        final int count = institutionCombo.getItemCount();
        assertEquals(Institution.OTHER, institutionCombo.getItemAt(count - 1));
        for (int i = 0; i < count - 2; i++) {
            final String current = institutionCombo.getItemAt(i).getDisplayName();
            final String next = institutionCombo.getItemAt(i + 1).getDisplayName();
            assertTrue(current.compareToIgnoreCase(next) <= 0);
        }
    }

    @Test
    void testNegativeHIndexFailsValidationAndDoesNotSubmit() {
        final UpdateProfileInputBoundary interactor = mock(UpdateProfileInputBoundary.class);
        final UpdateProfileView view = buildView(interactor);
        hIndexField(view).setText("-1");

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            saveButton(view).doClick();

            optionPane.verify(() -> JOptionPane.showMessageDialog(
                any(), any(), org.mockito.ArgumentMatchers.eq("Save Profile Failed"), org.mockito.ArgumentMatchers.eq(JOptionPane.ERROR_MESSAGE)));
        }
        verify(interactor, never()).execute(any());
    }

    @Test
    void testNonNumericCitationsFailsValidationAndDoesNotSubmit() {
        final UpdateProfileInputBoundary interactor = mock(UpdateProfileInputBoundary.class);
        final UpdateProfileView view = buildView(interactor);
        citationsField(view).setText("not-a-number");

        try (MockedStatic<JOptionPane> optionPane = mockStatic(JOptionPane.class)) {
            saveButton(view).doClick();

            optionPane.verify(() -> JOptionPane.showMessageDialog(
                any(), any(), org.mockito.ArgumentMatchers.eq("Save Profile Failed"), org.mockito.ArgumentMatchers.eq(JOptionPane.ERROR_MESSAGE)));
        }
        verify(interactor, never()).execute(any());
    }

    @Test
    void testBlankNumericFieldsAreSubmittedAsNullNotZero() {
        final UpdateProfileInputBoundary interactor = mock(UpdateProfileInputBoundary.class);
        final UpdateProfileView view = buildView(interactor);
        hIndexField(view).setText("");
        citationsField(view).setText("  ");
        weeklyAvailabilityField(view).setText("");

        saveButton(view).doClick();

        final ArgumentCaptor<UpdateProfileInputData> captor = ArgumentCaptor.forClass(UpdateProfileInputData.class);
        verify(interactor, timeout(2000)).execute(captor.capture());
        assertNull(captor.getValue().getHIndex());
        assertNull(captor.getValue().getTotalCitations());
        assertNull(captor.getValue().getWeeklyAvailabilityHours());
    }

    @Test
    void testValidNumericFieldsAreSubmittedAsParsedIntegers() {
        final UpdateProfileInputBoundary interactor = mock(UpdateProfileInputBoundary.class);
        final UpdateProfileView view = buildView(interactor);
        hIndexField(view).setText("12");
        citationsField(view).setText("340");
        weeklyAvailabilityField(view).setText("8");

        saveButton(view).doClick();

        final ArgumentCaptor<UpdateProfileInputData> captor = ArgumentCaptor.forClass(UpdateProfileInputData.class);
        verify(interactor, timeout(2000)).execute(captor.capture());
        assertEquals(12, captor.getValue().getHIndex());
        assertEquals(340, captor.getValue().getTotalCitations());
        assertEquals(8, captor.getValue().getWeeklyAvailabilityHours());
    }

    @Test
    void testLoadedProfilePreFillsTheEmailField() throws Exception {
        final UpdateProfileViewModel viewModel = new UpdateProfileViewModel();
        final UpdateProfileView view = new UpdateProfileView(
            new UpdateProfileController(mock(UpdateProfileInputBoundary.class)),
            new LoadProfileController(mock(LoadProfileInputBoundary.class)),
            viewModel,
            new PaperLookupController(mock(PaperLookupInputBoundary.class)),
            new PaperLookupViewModel());
        final UserData savedProfile = sampleUser();

        SwingUtilities.invokeAndWait(() -> viewModel.setCurrentUser(savedProfile));

        assertEquals("ada@example.com", SwingTestSupport.find(view, JTextField.class, 0).getText());
    }

    private UpdateProfileView buildView(final UpdateProfileInputBoundary interactor) {
        return new UpdateProfileView(
            new UpdateProfileController(interactor),
            new LoadProfileController(mock(LoadProfileInputBoundary.class)),
            new UpdateProfileViewModel(),
            new PaperLookupController(mock(PaperLookupInputBoundary.class)),
            new PaperLookupViewModel());
    }

    private JTextField hIndexField(final UpdateProfileView view) {
        return SwingTestSupport.find(view, JTextField.class, 3);
    }

    private JTextField citationsField(final UpdateProfileView view) {
        return SwingTestSupport.find(view, JTextField.class, 4);
    }

    private JTextField weeklyAvailabilityField(final UpdateProfileView view) {
        return SwingTestSupport.find(view, JTextField.class, 1);
    }

    /**
     * Multiple JScrollPanes in this view create their own scrollbar arrow JButtons, so a
     * plain index-based JButton search can hit one of those instead of the real button.
     */
    private JButton saveButton(final UpdateProfileView view) {
        for (final JButton button : SwingTestSupport.findAll(view, JButton.class)) {
            if ("Save Profile".equals(button.getText())) {
                return button;
            }
        }
        throw new IllegalStateException("Save Profile button not found");
    }

    private UserData sampleUser() {
        final User user = new User(
            "user-1", "Ada", "Lovelace", "ada@example.com", "555-0000",
            Institution.UNIVERSITY_OF_CAMBRIDGE, AcademicLevel.FACULTY, ResearchField.MACHINE_LEARNING,
            CollaborationType.CO_AUTHOR, "Looking for co-authors", "Analytical engines and algorithms",
            8, FundingStatus.INSTITUTIONAL_FUNDING, "hash");
        return UserData.from(user);
    }
}
