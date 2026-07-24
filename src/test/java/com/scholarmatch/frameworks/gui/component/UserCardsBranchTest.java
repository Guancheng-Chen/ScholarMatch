package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.AcademicLevel;
import com.scholarmatch.entity.CollaborationType;
import com.scholarmatch.entity.DegreeType;
import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.FundingStatus;
import com.scholarmatch.entity.Institution;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.entity.ResearchField;
import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;
import com.scholarmatch.usecase.dto.UserData;

import org.junit.jupiter.api.Test;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.time.Month;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCardsBranchTest {

    @Test
    void testRecommendCardFullAndEmptyProfilesButtonsReflowAndAvatarPaint() throws Exception {
        final AtomicInteger dislike = new AtomicInteger();
        final AtomicInteger skip = new AtomicInteger();
        final AtomicInteger connect = new AtomicInteger();
        SwingUtilities.invokeAndWait(() -> {
            final RecommendUserCard.ConnectListener listener = new RecommendUserCard.ConnectListener() {
                @Override
                public void onDislike() {
                    dislike.incrementAndGet();
                }

                @Override
                public void onSkip() {
                    skip.incrementAndGet();
                }

                @Override
                public void onConnect() {
                    connect.incrementAndGet();
                }
            };
            final RecommendUserCard full = new RecommendUserCard(fullUser(), listener);
            full.reflow(600);
            full.reflow(800);
            full.reflow(100);
            full.reflow(5000);
            for (final JButton button : SwingTestSupport.findAll(full, JButton.class)) {
                button.doClick();
            }
            paintNestedCustomComponents(full);

            final RecommendUserCard empty = new RecommendUserCard(emptyUser(null, " "), listener);
            empty.reflow(700);
            paintNestedCustomComponents(empty);
        });
        assertEquals(1, dislike.get());
        assertEquals(1, skip.get());
        assertEquals(1, connect.get());
    }

    @Test
    void testMatchedCardFullAndNullBlankProfilesAndAvatarPaint() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            final MatchedUserCard full = new MatchedUserCard(fullUser());
            full.reflow(600);
            full.reflow(800);
            paintNestedCustomComponents(full);
            try {
                final Method join = MatchedUserCard.class.getDeclaredMethod(
                        "joinNonBlank", String.class, String[].class);
                join.setAccessible(true);
                assertEquals("kept", join.invoke(full, ", ", new String[] {null, " ", "kept"}));
            } catch (ReflectiveOperationException ex) {
                throw new IllegalStateException(ex);
            }

            final MatchedUserCard empty = new MatchedUserCard(emptyUser(null, ""));
            empty.reflow(700);
            paintNestedCustomComponents(empty);
        });
    }

    private static UserData fullUser() {
        return new UserData(
                "user-1", "Ada", "Lovelace", "ada&test@example.com", "555<0000",
                Institution.MIT, AcademicLevel.GRADUATE_STUDENT, ResearchField.MACHINE_LEARNING,
                CollaborationType.CO_AUTHOR, "Build <models> & systems",
                "Research <description> & more", 8, FundingStatus.INSTITUTIONAL_FUNDING,
                List.of("AI", "Systems"),
                List.of(
                        new Education("MIT", DegreeType.PHD, 2020, Month.JANUARY, null, null),
                        new Education("Cambridge", DegreeType.MASTER, 2017, Month.JANUARY, 2019, Month.MAY),
                        new Education("Unknown", DegreeType.BACHELOR, 0, null, 2020, null)),
                List.of(
                        new Publication("doi", "Paper & <One>", 2024, 5),
                        new Publication("", "Undated", 0, 0)),
                10, 200);
    }

    private static UserData emptyUser(final String firstName, final String lastName) {
        return new UserData(
                "user-2", firstName, lastName, "", "", null, null, null, null,
                "", "", null, null, List.of(), List.of(), List.of(), null, null);
    }

    private static void paintNestedCustomComponents(final JComponent root) {
        for (final Component component : SwingTestSupport.findAll(root, JComponent.class)) {
            if (component.getClass().getName().contains("CircleAvatar")) {
                component.setSize(component.getPreferredSize());
                final BufferedImage image = new BufferedImage(
                        component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
                final Graphics2D graphics = image.createGraphics();
                try {
                    component.paint(graphics);
                } finally {
                    graphics.dispose();
                }
            }
        }
    }
}
